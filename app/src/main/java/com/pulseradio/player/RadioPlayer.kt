package com.pulseradio.player

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.okhttp.OkHttpDataSource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

@UnstableApi
class RadioPlayer(private val context: Context) {

    private var exoPlayer: ExoPlayer? = null
    private var playbackService: RadioPlaybackService? = null
    private var serviceBound = false

    val isPlaying: StateFlow<Boolean> get() = _isPlaying
    private val _isPlaying = MutableStateFlow(false)

    val currentTitle: StateFlow<String> get() = _currentTitle
    private val _currentTitle = MutableStateFlow("")

    val currentArtist: StateFlow<String> get() = _currentArtist
    private val _currentArtist = MutableStateFlow("")

    val currentCoverUrl: StateFlow<String> get() = _currentCoverUrl
    private val _currentCoverUrl = MutableStateFlow("")

    private val metadataExtractor = StreamMetadata()
    private val coverFetcher = CoverArtFetcher()
    private var metadataJob: Job? = null
    private var serviceIntent: Intent? = null

    init {
        initializePlayer()
        startPlaybackService()
    }

    private fun initializePlayer() {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()

        val okHttpFactory = OkHttpDataSource.Factory(httpClient)
        val defaultFactory = DefaultDataSource.Factory(context, okHttpFactory)

        exoPlayer = ExoPlayer.Builder(context)
            .setMediaSourceFactory(ProgressiveMediaSource.Factory(defaultFactory))
            .build()
            .apply {
                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(state: Int) {
                        _isPlaying.value = state == Player.STATE_READY && playWhenReady
                    }

                    override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
                        _isPlaying.value = playWhenReady && playbackState == Player.STATE_READY
                    }
                })
            }
    }

    private fun startPlaybackService() {
        serviceIntent = Intent(context, RadioPlaybackService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent)
        } else {
            context.startService(serviceIntent)
        }
    }

    fun play(url: String) {
        exoPlayer?.let { player ->
            val mediaItem = MediaItem.fromUri(url)
            player.setMediaItem(mediaItem)
            player.prepare()
            player.playWhenReady = true
            _isPlaying.value = true

            // Iniciar extraccion de metadata
            startMetadataExtraction(url)

            // Solicitar audio focus
            playbackService?.requestAudioFocus()
        }
    }

    fun pause() {
        exoPlayer?.playWhenReady = false
        _isPlaying.value = false
        metadataJob?.cancel()
        playbackService?.abandonAudioFocus()
    }

    fun toggle() {
        exoPlayer?.let {
            if (it.isPlaying) pause() else {
                it.playWhenReady = true
                _isPlaying.value = true
            }
        }
    }

    private fun startMetadataExtraction(url: String) {
        metadataJob?.cancel()
        metadataJob = CoroutineScope(Dispatchers.IO).launch {
            metadataExtractor.extractMetadata(url) { title, artist ->
                _currentTitle.value = title
                _currentArtist.value = artist

                // Buscar cover art
                if (artist.isNotBlank() && title.isNotBlank()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val cover = coverFetcher.fetchCoverArt(artist, title)
                        cover?.let { _currentCoverUrl.value = it }
                    }
                }

                // Actualizar servicio de notificacion
                playbackService?.updateMetadata(title, artist, _currentCoverUrl.value)
            }
        }
    }

    /**
     * Actualiza la informacion de la emisora en el servicio de notificacion.
     */
    fun updateStationInfo(name: String, genre: String, imageUrl: String) {
        playbackService?.updateStationInfo(name, genre, imageUrl, _isPlaying.value)
    }

    fun release() {
        metadataJob?.cancel()
        exoPlayer?.release()
        exoPlayer = null
        serviceIntent?.let {
            context.stopService(it)
        }
    }
}
