package com.pulseradio.player

import kotlinx.coroutines.*
import java.net.HttpURLConnection
import java.net.URL

class MetadataExtractor {
    private var job: Job? = null
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun startMonitoring(url: String, onMetadata: (String) -> Unit) {
        stopMonitoring()
        job = scope.launch {
            extractMetadata(url, onMetadata)
        }
    }

    fun stopMonitoring() {
        job?.cancel()
        job = null
    }

    private suspend fun extractMetadata(url: String, onMetadata: (String) -> Unit) {
        withContext(Dispatchers.IO) {
            var connection: HttpURLConnection? = null
            try {
                val urlObj = URL(url)
                connection = urlObj.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Icy-Metadata", "1")
                connection.setRequestProperty("User-Agent", "PulseRadio/1.0")
                connection.connectTimeout = 10000
                connection.readTimeout = 10000
                connection.connect()

                val metaint = connection.getHeaderField("icy-metaint")?.toIntOrNull()
                if (metaint == null || metaint <= 0) {
                    onMetadata("Metadatos no disponibles")
                    return@withContext
                }

                val input = connection.inputStream
                val buffer = ByteArray(metaint)
                val metaBuffer = ByteArray(4081) // max 255 * 16

                while (isActive) {
                    // Leer bloque de audio (descartado)
                    var read = 0
                    while (read < metaint && isActive) {
                        val r = input.read(buffer, read, metaint - read)
                        if (r == -1) return@withContext
                        read += r
                    }

                    // Leer longitud de metadata
                    val metaLength = input.read()
                    if (metaLength == -1) return@withContext
                    val length = metaLength * 16

                    if (length > 0) {
                        var metaRead = 0
                        while (metaRead < length && isActive) {
                            val r = input.read(metaBuffer, metaRead, length - metaRead)
                            if (r == -1) return@withContext
                            metaRead += r
                        }
                        val metaString = String(metaBuffer, 0, length, Charsets.UTF_8)
                        val title = parseStreamTitle(metaString)
                        if (title != null && title.isNotBlank()) {
                            withContext(Dispatchers.Main) {
                                onMetadata(title)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                if (isActive) {
                    onMetadata("Sin metadatos")
                }
            } finally {
                connection?.disconnect()
            }
        }
    }

    private fun parseStreamTitle(meta: String): String? {
        val regex = "StreamTitle='([^']*)'".toRegex()
        return regex.find(meta)?.groupValues?.get(1)
    }
}
