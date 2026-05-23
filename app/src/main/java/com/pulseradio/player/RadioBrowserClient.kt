package com.pulseradio.player

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLEncoder
import java.util.concurrent.TimeUnit

/**
 * Cliente para la API de Radio Browser.
 * Base de datos comunitaria con +45,000 emisoras de internet.
 * COMPLETAMENTE GRATIS. Sin API key. Sin registro.
 * https://api.radio-browser.info
 */
class RadioBrowserClient {

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    // Servidores de la red Radio Browser (seleccionamos uno aleatorio)
    private val servers = listOf(
        "https://de1.api.radio-browser.info",
        "https://nl1.api.radio-browser.info",
        "https://fr1.api.radio-browser.info",
        "https://at1.api.radio-browser.info"
    )

    private fun getServer(): String = servers.random()

    /**
     * Busca emisoras por nombre.
     * @param query Texto a buscar (nombre de emisora, genero, etc.)
     * @param limit Maximo de resultados (default 50)
     * @return Lista de emisoras encontradas
     */
    suspend fun searchStations(query: String, limit: Int = 50): List<RadioBrowserStation> {
        if (query.isBlank()) return emptyList()

        val encoded = URLEncoder.encode(query, "UTF-8")
        val url = "${getServer()}/json/stations/search?name=$encoded&limit=$limit&order=clickcount&reverse=true"

        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder()
                    .url(url)
                    .header("User-Agent", "PulseRadio/1.0")
                    .build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        Log.w("RadioBrowser", "Error: ${response.code}")
                        return@withContext emptyList()
                    }

                    val body = response.body?.string() ?: return@withContext emptyList()
                    val jsonArray = JSONArray(body)
                    val results = mutableListOf<RadioBrowserStation>()

                    for (i in 0 until jsonArray.length()) {
                        val obj = jsonArray.getJSONObject(i)
                        val station = RadioBrowserStation(
                            stationuuid = obj.optString("stationuuid", ""),
                            name = obj.optString("name", "Sin nombre").trim(),
                            urlResolved = obj.optString("url_resolved", ""),
                            url = obj.optString("url", ""),
                            homepage = obj.optString("homepage", ""),
                            favicon = obj.optString("favicon", ""),
                            country = obj.optString("country", ""),
                            countrycode = obj.optString("countrycode", ""),
                            language = obj.optString("language", ""),
                            tags = obj.optString("tags", ""),
                            bitrate = obj.optInt("bitrate", 0),
                            clickcount = obj.optInt("clickcount", 0),
                            votes = obj.optInt("votes", 0)
                        )
                        // Solo incluir si tiene URL de stream valida
                        if (station.streamUrl.isNotBlank()) {
                            results.add(station)
                        }
                    }

                    Log.d("RadioBrowser", "Encontradas: ${results.size} emisoras para '$query'")
                    results
                }
            } catch (e: Exception) {
                Log.e("RadioBrowser", "Error buscando: ${e.message}")
                emptyList()
            }
        }
    }

    /**
     * Busca emisoras por pais.
     */
    suspend fun searchByCountry(countryCode: String, limit: Int = 30): List<RadioBrowserStation> {
        val url = "${getServer()}/json/stations/bycountrycodeexact/$countryCode?limit=$limit&order=clickcount&reverse=true"

        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder()
                    .url(url)
                    .header("User-Agent", "PulseRadio/1.0")
                    .build()

                client.newCall(request).execute().use { response ->
                    val body = response.body?.string() ?: return@withContext emptyList()
                    parseStations(JSONArray(body))
                }
            } catch (e: Exception) {
                Log.e("RadioBrowser", "Error por pais: ${e.message}")
                emptyList()
            }
        }
    }

    /**
     * Busca emisoras por tag/genero.
     */
    suspend fun searchByTag(tag: String, limit: Int = 30): List<RadioBrowserStation> {
        val encoded = URLEncoder.encode(tag, "UTF-8")
        val url = "${getServer()}/json/stations/bytag/$encoded?limit=$limit&order=clickcount&reverse=true"

        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder()
                    .url(url)
                    .header("User-Agent", "PulseRadio/1.0")
                    .build()

                client.newCall(request).execute().use { response ->
                    val body = response.body?.string() ?: return@withContext emptyList()
                    parseStations(JSONArray(body))
                }
            } catch (e: Exception) {
                Log.e("RadioBrowser", "Error por tag: ${e.message}")
                emptyList()
            }
        }
    }

    /**
     * Emisoras mas populares globalmente.
     */
    suspend fun getTopStations(limit: Int = 50): List<RadioBrowserStation> {
        val url = "${getServer()}/json/stations/topclick/$limit"

        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder()
                    .url(url)
                    .header("User-Agent", "PulseRadio/1.0")
                    .build()

                client.newCall(request).execute().use { response ->
                    val body = response.body?.string() ?: return@withContext emptyList()
                    parseStations(JSONArray(body))
                }
            } catch (e: Exception) {
                Log.e("RadioBrowser", "Error top stations: ${e.message}")
                emptyList()
            }
        }
    }

    private fun parseStations(array: JSONArray): List<RadioBrowserStation> {
        val results = mutableListOf<RadioBrowserStation>()
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            val station = RadioBrowserStation(
                stationuuid = obj.optString("stationuuid", ""),
                name = obj.optString("name", "Sin nombre").trim(),
                urlResolved = obj.optString("url_resolved", ""),
                url = obj.optString("url", ""),
                homepage = obj.optString("homepage", ""),
                favicon = obj.optString("favicon", ""),
                country = obj.optString("country", ""),
                countrycode = obj.optString("countrycode", ""),
                language = obj.optString("language", ""),
                tags = obj.optString("tags", ""),
                bitrate = obj.optInt("bitrate", 0),
                clickcount = obj.optInt("clickcount", 0),
                votes = obj.optInt("votes", 0)
            )
            if (station.streamUrl.isNotBlank()) {
                results.add(station)
            }
        }
        return results
    }

    /**
     * Notifica a Radio Browser que se hizo clic en una emisora.
     * Esto ayuda a que la base de datos se mantenga util.
     */
    suspend fun sendClick(stationUuid: String) {
        withContext(Dispatchers.IO) {
            try {
                val url = "${getServer()}/json/url/$stationUuid"
                val request = Request.Builder()
                    .url(url)
                    .header("User-Agent", "PulseRadio/1.0")
                    .build()
                client.newCall(request).execute().close()
            } catch (_: Exception) { }
        }
    }
}

/**
 * Modelo de emisora desde Radio Browser API.
 */
data class RadioBrowserStation(
    val stationuuid: String,
    val name: String,
    val urlResolved: String,
    val url: String,
    val homepage: String,
    val favicon: String,
    val country: String,
    val countrycode: String,
    val language: String,
    val tags: String,
    val bitrate: Int,
    val clickcount: Int,
    val votes: Int
) {
    /** URL de stream a usar (preferimos url_resolved) */
    val streamUrl: String get() = urlResolved.takeIf { it.isNotBlank() } ?: url

    /** URL de imagen (favicon o fallback) */
    val imageUrl: String get() = favicon.takeIf { it.isNotBlank() && it.startsWith("http") }
        ?: "https://images.unsplash.com/photo-1470225620780-dba8ba36b745?auto=format&fit=crop&q=80&w=400"

    /** Tags formateados */
    val genre: String get() = tags.split(",").firstOrNull()?.trim()?.replace(" ", " ") ?: "Radio"

    /** Ubicacion formateada */
    val location: String get() = if (country.isNotBlank()) country else "Internet"
}
