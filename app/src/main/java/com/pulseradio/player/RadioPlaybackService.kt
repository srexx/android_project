package com.pulseradio.player

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.media.session.MediaButtonReceiver
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.pulseradio.MainActivity
import com.pulseradio.R
import kotlinx.coroutines.*

/**
 * Foreground Service para reproduccion de radio en segundo plano.
 * Integra MediaSession para controles nativos de Android:
 * - Notificacion de medios con play/pause
 * - Controles en lock screen
 * - Widget del reproductor
 * - Audio focus handling
 */
class RadioPlaybackService : Service() {

    companion object {
        const val CHANNEL_ID = "PulseRadioPlayback"
        const val NOTIFICATION_ID = 1001
        const val ACTION_PLAY = "com.pulseradio.ACTION_PLAY"
        const val ACTION_PAUSE = "com.pulseradio.ACTION_PAUSE"
        const val ACTION_STOP = "com.pulseradio.ACTION_STOP"
    }

    private val binder = RadioBinder()
    private var mediaSession: MediaSessionCompat? = null
    private var audioManager: AudioManager? = null
    private var audioFocusRequest: AudioFocusRequest? = null
    private var currentStationName = "PulseRadio"
    private var currentStationGenre = "Streaming"
    private var currentImageUrl = ""
    private var isPlaying = false
    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    inner class RadioBinder : Binder() {
        fun getService(): RadioPlaybackService = this@RadioPlaybackService
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        initMediaSession()
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    override fun onBind(intent: Intent): IBinder = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        MediaButtonReceiver.handleIntent(mediaSession, intent)

        when (intent?.action) {
            ACTION_PLAY -> {
                // La reproduccion real la controla RadioPlayer desde el ViewModel
                updatePlaybackState(true)
            }
            ACTION_PAUSE -> {
                updatePlaybackState(false)
            }
            ACTION_STOP -> {
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
            }
        }

        return START_STICKY
    }

    /**
     * Actualiza la informacion de la emisora actual y muestra la notificacion.
     */
    fun updateStationInfo(name: String, genre: String, imageUrl: String, playing: Boolean) {
        currentStationName = name
        currentStationGenre = genre
        currentImageUrl = imageUrl
        isPlaying = playing

        mediaSession?.setMetadata(
            androidx.media2.common.MediaMetadata.Builder()
                .putString(androidx.media2.common.MediaMetadata.METADATA_KEY_TITLE, name)
                .putString(androidx.media2.common.MediaMetadata.METADATA_KEY_ARTIST, genre)
                .build()
                .let {
                    // Convert to MediaSessionCompat metadata
                    android.support.v4.media.MediaMetadataCompat.Builder()
                        .putString(android.support.v4.media.MediaMetadataCompat.METADATA_KEY_TITLE, name)
                        .putString(android.support.v4.media.MediaMetadataCompat.METADATA_KEY_ARTIST, genre)
                        .putString(android.support.v4.media.MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, imageUrl)
                        .build()
                }
        )

        updatePlaybackState(playing)
        showNotification()
    }

    /**
     * Actualiza el estado de reproduccion (metadatos dinamicos).
     */
    fun updateMetadata(title: String, artist: String, imageUrl: String) {
        mediaSession?.setMetadata(
            android.support.v4.media.MediaMetadataCompat.Builder()
                .putString(android.support.v4.media.MediaMetadataCompat.METADATA_KEY_TITLE, title)
                .putString(android.support.v4.media.MediaMetadataCompat.METADATA_KEY_ARTIST, artist)
                .putString(android.support.v4.media.MediaMetadataCompat.METADATA_KEY_ALBUM, currentStationName)
                .putString(android.support.v4.media.MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, imageUrl)
                .build()
        )
        showNotification()
    }

    private fun initMediaSession() {
        mediaSession = MediaSessionCompat(this, "PulseRadio").apply {
            setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
            setCallback(object : MediaSessionCompat.Callback() {
                override fun onPlay() {
                    // Enviar broadcast para que el ViewModel maneje
                    sendBroadcast(Intent(ACTION_PLAY).setPackage(packageName))
                }

                override fun onPause() {
                    sendBroadcast(Intent(ACTION_PAUSE).setPackage(packageName))
                }

                override fun onStop() {
                    sendBroadcast(Intent(ACTION_STOP).setPackage(packageName))
                }
            })
            isActive = true
        }
    }

    private fun updatePlaybackState(playing: Boolean) {
        val state = if (playing) PlaybackStateCompat.STATE_PLAYING else PlaybackStateCompat.STATE_PAUSED
        val actions = PlaybackStateCompat.ACTION_PLAY or
                PlaybackStateCompat.ACTION_PAUSE or
                PlaybackStateCompat.ACTION_STOP

        mediaSession?.setPlaybackState(
            PlaybackStateCompat.Builder()
                .setState(state, 0, 1.0f)
                .setActions(actions)
                .build()
        )
    }

    private fun showNotification() {
        val playIntent = PendingIntent.getBroadcast(
            this, 0, Intent(ACTION_PLAY).setPackage(packageName),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val pauseIntent = PendingIntent.getBroadcast(
            this, 1, Intent(ACTION_PAUSE).setPackage(packageName),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val stopIntent = PendingIntent.getBroadcast(
            this, 2, Intent(ACTION_STOP).setPackage(packageName),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val contentIntent = PendingIntent.getActivity(
            this, 0, Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val playPauseAction = if (isPlaying) {
            NotificationCompat.Action(
                R.drawable.ic_pause, "Pausar", pauseIntent
            )
        } else {
            NotificationCompat.Action(
                R.drawable.ic_play, "Reproducir", playIntent
            )
        }

        val stopAction = NotificationCompat.Action(
            R.drawable.ic_stop, "Detener", stopIntent
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(currentStationName)
            .setContentText(currentStationGenre)
            .setSubText("PulseRadio - En directo")
            .setSmallIcon(R.drawable.ic_notification)
            .setLargeIcon(null) // Se carga async abajo
            .setContentIntent(contentIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(isPlaying)
            .setOnlyAlertOnce(true)
            .addAction(playPauseAction)
            .addAction(stopAction)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSession?.sessionToken)
                    .setShowActionsInCompactView(0)
            )
            .build()

        startForeground(NOTIFICATION_ID, notification)

        // Cargar imagen async para la notificacion
        if (currentImageUrl.isNotBlank()) {
            serviceScope.launch {
                val bitmap = loadImageBitmap(currentImageUrl)
                bitmap?.let {
                    val updatedNotification = NotificationCompat.Builder(this@RadioPlaybackService, CHANNEL_ID)
                        .setContentTitle(currentStationName)
                        .setContentText(currentStationGenre)
                        .setSubText("PulseRadio - En directo")
                        .setSmallIcon(R.drawable.ic_notification)
                        .setLargeIcon(it)
                        .setContentIntent(contentIntent)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setOngoing(isPlaying)
                        .setOnlyAlertOnce(true)
                        .addAction(playPauseAction)
                        .addAction(stopAction)
                        .setStyle(
                            androidx.media.app.NotificationCompat.MediaStyle()
                                .setMediaSession(mediaSession?.sessionToken)
                                .setShowActionsInCompactView(0)
                        )
                        .build()

                    val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    nm.notify(NOTIFICATION_ID, updatedNotification)
                }
            }
        }
    }

    private suspend fun loadImageBitmap(url: String): Bitmap? = withContext(Dispatchers.IO) {
        try {
            val loader = ImageLoader(this@RadioPlaybackService)
            val request = ImageRequest.Builder(this@RadioPlaybackService)
                .data(url)
                .allowHardware(false)
                .build()
            val result = loader.execute(request)
            if (result is SuccessResult) {
                (result.drawable as? BitmapDrawable)?.bitmap
            } else null
        } catch (e: Exception) {
            null
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Reproduccion de Radio",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Notificacion para controlar la reproduccion de PulseRadio"
                setShowBadge(false)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    /**
     * Solicita audio focus para la reproduccion.
     */
    fun requestAudioFocus(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val focusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                .setOnAudioFocusChangeListener { focusChange ->
                    when (focusChange) {
                        AudioManager.AUDIOFOCUS_LOSS -> {
                            // Perdida permanente - pausar
                            sendBroadcast(Intent(ACTION_PAUSE).setPackage(packageName))
                        }
                        AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                            // Perdida temporal - pausar
                            sendBroadcast(Intent(ACTION_PAUSE).setPackage(packageName))
                        }
                        AudioManager.AUDIOFOCUS_GAIN -> {
                            // Recuperado - reanudar
                            sendBroadcast(Intent(ACTION_PLAY).setPackage(packageName))
                        }
                    }
                }
                .build()
            audioFocusRequest = focusRequest
            audioManager?.requestAudioFocus(focusRequest) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
        } else {
            @Suppress("DEPRECATION")
            audioManager?.requestAudioFocus(
                { focusChange ->
                    when (focusChange) {
                        AudioManager.AUDIOFOCUS_LOSS,
                        AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                            sendBroadcast(Intent(ACTION_PAUSE).setPackage(packageName))
                        }
                        AudioManager.AUDIOFOCUS_GAIN -> {
                            sendBroadcast(Intent(ACTION_PLAY).setPackage(packageName))
                        }
                    }
                },
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN
            ) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
        }
    }

    fun abandonAudioFocus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioFocusRequest?.let { audioManager?.abandonAudioFocusRequest(it) }
        } else {
            @Suppress("DEPRECATION")
            audioManager?.abandonAudioFocus(null)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        abandonAudioFocus()
        mediaSession?.release()
        serviceScope.cancel()
    }
}
