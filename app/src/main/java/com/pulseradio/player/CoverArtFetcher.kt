package com.pulseradio.player

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.net.URLEncoder
import java.util.concurrent.TimeUnit

/**
 * Buscador de caratulas de album usando la API gratuita de iTunes.
 * No requiere API key. Usa OkHttp para consultas HTTP.
 *
 * Truco de resolucion: iTunes devuelve URLs como .../100x100bb.jpg
 * Cambiando a .../3000x3000bb.jpg se obtiene la imagen en alta resolucion.
 */
class CoverArtFetcher {

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()

    /** Cache simple en memoria para no repetir busquedas */
    private val cache = mutableMapOf<String, String?>()

    /**
     * Busca la caratula de una cancion por artista y titulo.
     * @return URL de la imagen en alta resolucion, o null si no encuentra.
     */
    suspend fun search(artist: String, title: String): String? {
        val key = "$artist|$title"
        cache[key]?.let { return it }

        val query = URLEncoder.encode("$artist $title", "UTF-8")
        val url = "https://itunes.apple.com/search?term=$query&entity=song&limit=1"

        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder()
                    .url(url)
                    .header("User-Agent", "PulseRadio/1.0")
                    .build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        Log.w("CoverArt", "iTunes API error: ${response.code}")
                        cache[key] = null
                        return@withContext null
                    }

                    val body = response.body?.string() ?: run {
                        cache[key] = null
                        return@withContext null
                    }

                    val json = JSONObject(body)
                    val results = json.getJSONArray("results")

                    if (results.length() == 0) {
                        Log.d("CoverArt", "Sin resultados para: $artist - $title")
                        cache[key] = null
                        return@withContext null
                    }

                    val track = results.getJSONObject(0)
                    val artworkUrl = track.optString("artworkUrl100", "")

                    if (artworkUrl.isBlank()) {
                        cache[key] = null
                        return@withContext null
                    }

                    // Truco de resolucion: 100x100 -> 3000x3000 para alta calidad
                    val highResUrl = artworkUrl
                        .replace("100x100bb", "3000x3000bb")
                        .replace("100x100", "3000x3000")

                    Log.d("CoverArt", "Encontrada caratula: $highResUrl")
                    cache[key] = highResUrl
                    highResUrl
                }
            } catch (e: Exception) {
                Log.e("CoverArt", "Error buscando caratula: ${e.message}")
                cache[key] = null
                null
            }
        }
    }

    fun clearCache() {
        cache.clear()
    }
}
