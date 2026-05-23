package com.pulseradio.player

import android.content.Context
import android.util.Log
import com.google.android.gms.cast.*
import com.google.android.gms.cast.framework.*
import com.google.android.gms.cast.framework.media.RemoteMediaClient
import com.google.android.gms.common.api.Status

/**
 * Gestiona la conexion y transmision a Chromecast/Google Cast.
 * Permite enviar el stream de radio a dispositivos Cast (Chromecast, Google Home, etc.)
 * 
 * NOTA: Requiere un Cast App ID registrado en Google Cast SDK Developer Console.
 * Para pruebas, usa el Default Media Receiver: CC1AD845
 */
class ChromecastManager(context: Context) {

    companion object {
        // Default Media Receiver ID de Google (para pruebas)
        // Para produccion, registra tu app en: https://cast.google.com/publish
        const val CAST_APP_ID = "CC1AD845"
        private const val TAG = "ChromecastManager"
    }

    private var castContext: CastContext? = null
    private var castSession: CastSession? = null
    private var sessionManager: SessionManager? = null
    private var remoteMediaClient: RemoteMediaClient? = null

    val isConnected: Boolean get() = castSession?.isConnected == true

    init {
        try {
            // Inicializar CastContext
            val castOptions = CastOptions.Builder()
                .setReceiverApplicationId(CAST_APP_ID)
                .build()

            val optionsProvider = object : OptionsProvider {
                override fun getCastOptions(context: Context): CastOptions = castOptions
                override fun getAdditionalSessionProviders(context: Context): List<SessionProvider>? = null
            }

            CastContext.getSharedInstance(context, optionsProvider)
            castContext = CastContext.getSharedInstance(context)
            sessionManager = castContext?.sessionManager

            // Escuchar cambios de sesion
            sessionManager?.addSessionManagerListener(object : SessionManagerListener<CastSession> {
                override fun onSessionStarting(session: CastSession) {
                    Log.d(TAG, "Session starting...")
                }
                override fun onSessionStarted(session: CastSession, sessionId: String) {
                    Log.d(TAG, "Session started: $sessionId")
                    castSession = session
                    remoteMediaClient = session.remoteMediaClient
                }
                override fun onSessionStartFailed(session: CastSession, error: Int) {
                    Log.e(TAG, "Session start failed: $error")
                }
                override fun onSessionEnding(session: CastSession) {
                    Log.d(TAG, "Session ending...")
                }
                override fun onSessionEnded(session: CastSession, error: Int) {
                    Log.d(TAG, "Session ended")
                    castSession = null
                    remoteMediaClient = null
                }
                override fun onSessionResuming(session: CastSession, sessionId: String) {
                    Log.d(TAG, "Session resuming...")
                }
                override fun onSessionResumed(session: CastSession, wasSuspended: Boolean) {
                    Log.d(TAG, "Session resumed")
                    castSession = session
                    remoteMediaClient = session.remoteMediaClient
                }
                override fun onSessionResumeFailed(session: CastSession, error: Int) {
                    Log.e(TAG, "Session resume failed: $error")
                }
                override fun onSessionSuspended(session: CastSession, reason: Int) {
                    Log.d(TAG, "Session suspended")
                }
            }, CastSession::class.java)

        } catch (e: Exception) {
            Log.e(TAG, "Error initializing Cast: ${e.message}")
        }
    }

    /**
     * Inicia la transmision de un stream de radio a Chromecast.
     */
    fun castStream(streamUrl: String, title: String, artist: String, imageUrl: String) {
        val client = remoteMediaClient ?: run {
            Log.w(TAG, "No remote media client available")
            return
        }

        val mediaInfo = MediaInfo.Builder(streamUrl)
            .setStreamType(MediaInfo.STREAM_TYPE_LIVE)
            .setContentType("audio/mpeg")
            .setMetadata(MediaMetadata(MediaMetadata.MEDIA_TYPE_MUSIC_TRACK).apply {
                putString(MediaMetadata.KEY_TITLE, title)
                putString(MediaMetadata.KEY_ARTIST, artist)
                putString(MediaMetadata.KEY_ALBUM_TITLE, "PulseRadio")
                if (imageUrl.isNotBlank()) {
                    addImage(WebImage(android.net.Uri.parse(imageUrl)))
                }
            })
            .build()

        val mediaLoadOptions = MediaLoadOptions.Builder()
            .setAutoplay(true)
            .build()

        client.load(mediaInfo, mediaLoadOptions)
            .setResultCallback { result ->
                if (result.status.isSuccess) {
                    Log.d(TAG, "Stream loaded on Chromecast")
                } else {
                    Log.e(TAG, "Failed to load stream: ${result.status.statusMessage}")
                }
            }
    }

    /**
     * Controla play/pause en el Chromecast.
     */
    fun togglePlayPause() {
        remoteMediaClient?.let { client ->
            if (client.isPlaying) {
                client.pause()
            } else {
                client.play()
            }
        }
    }

    /**
     * Detiene la transmision.
     */
    fun stopCasting() {
        remoteMediaClient?.stop()
        sessionManager?.endCurrentSession(true)
    }

    /**
     * Obtiene el MediaRouter para mostrar el selector de dispositivos.
     */
    fun getCastContext(): CastContext? = castContext

    fun release() {
        stopCasting()
        castSession = null
        remoteMediaClient = null
    }
}
