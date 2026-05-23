package com.pulseradio.player

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers

// Extension para DataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "pulse_settings")

/**
 * Motor de recomendaciones estilo Spotify/TuneIn.
 * Aprende de lo que escuchas y sugiere emisoras similares.
 */
class RecommendationEngine(context: Context) {

    private val dataStore = context.dataStore
    private val radioBrowser = RadioBrowserClient()

    // Keys para DataStore
    private val LISTENED_TAGS = stringSetPreferencesKey("listened_tags")
    private val LISTENED_COUNTRIES = stringSetPreferencesKey("listened_countries")
    private val LISTENED_STATIONS = stringSetPreferencesKey("listened_station_uuids")
    private val FAVORITE_STATIONS = stringSetPreferencesKey("favorite_stations")
    private val LISTEN_COUNT = intPreferencesKey("listen_count")
    private val LAST_WEEKLY_REFRESH = longPreferencesKey("last_weekly_refresh")

    /**
     * Registra que el usuario escucho una emisora.
     * Esto alimenta el algoritmo de recomendaciones.
     */
    suspend fun recordListen(station: RadioBrowserStation) {
        dataStore.edit { prefs ->
            // Guardar tags
            val currentTags = prefs[LISTENED_TAGS] ?: emptySet()
            val newTags = station.tags.split(",").map { it.trim().lowercase() }.filter { it.isNotBlank() }
            prefs[LISTENED_TAGS] = currentTags + newTags

            // Guardar pais
            val currentCountries = prefs[LISTENED_COUNTRIES] ?: emptySet()
            prefs[LISTENED_COUNTRIES] = currentCountries + station.countrycode.lowercase()

            // Guardar UUID
            val currentStations = prefs[LISTENED_STATIONS] ?: emptySet()
            prefs[LISTENED_STATIONS] = currentStations + station.stationuuid

            // Incrementar contador
            val count = prefs[LISTEN_COUNT] ?: 0
            prefs[LISTEN_COUNT] = count + 1
        }
    }

    suspend fun recordLocalListen(station: com.pulseradio.data.RadioStation) {
        dataStore.edit { prefs ->
            val currentTags = prefs[LISTENED_TAGS] ?: emptySet()
            prefs[LISTENED_TAGS] = currentTags + station.genre.lowercase().split("/").map { it.trim() }

            val currentCountries = prefs[LISTENED_COUNTRIES] ?: emptySet()
            val countryCode = when {
                station.location.contains("USA", true) || station.location.contains("US", true) -> "us"
                station.location.contains("UK", true) -> "gb"
                station.location.contains("France", true) -> "fr"
                station.location.contains("Germany", true) -> "de"
                station.location.contains("Colombia", true) -> "co"
                station.location.contains("Mexico", true) -> "mx"
                station.location.contains("Brazil", true) -> "br"
                station.location.contains("Spain", true) -> "es"
                station.location.contains("Japan", true) -> "jp"
                else -> "us"
            }
            prefs[LISTENED_COUNTRIES] = currentCountries + countryCode
        }
    }

    /**
     * Obtiene recomendaciones "Hecho para ti" basadas en historial.
     */
    suspend fun getMadeForYou(): List<RadioBrowserStation> {
        val tags = getTopTags()
        if (tags.isEmpty()) {
            return radioBrowser.getTopStations(20)
        }

        val results = mutableListOf<RadioBrowserStation>()
        // Buscar emisoras con los tags mas escuchados
        for (tag in tags.take(3)) {
            val stations = radioBrowser.searchByTag(tag, limit = 10)
            results.addAll(stations)
        }
        return results.distinctBy { it.stationuuid }.shuffled().take(20)
    }

    /**
     * "Descubrimiento semanal" - emisoras nuevas basadas en tus gustos.
     */
    suspend fun getWeeklyDiscovery(): List<RadioBrowserStation> {
        val tags = getTopTags()
        val allResults = mutableListOf<RadioBrowserStation>()

        // Buscar emisoras menos populares (descubrimiento)
        for (tag in tags.shuffled().take(2)) {
            val stations = radioBrowser.searchByTag(tag, limit = 30)
            // Filtrar las menos populares (menos de 100 clicks)
            val underground = stations.filter { it.clickcount < 100 }
            allResults.addAll(underground)
        }

        return if (allResults.isNotEmpty()) {
            allResults.shuffled().take(15)
        } else {
            // Fallback: emisoras aleatorias populares
            radioBrowser.getTopStations(15)
        }
    }

    /**
     * "Mas como esto" basado en la emisora actual.
     */
    suspend fun getMoreLikeThis(station: RadioBrowserStation): List<RadioBrowserStation> {
        val tags = station.tags.split(",").map { it.trim() }.filter { it.isNotBlank() }
        if (tags.isEmpty()) return emptyList()

        val results = mutableListOf<RadioBrowserStation>()
        for (tag in tags.take(2)) {
            val similar = radioBrowser.searchByTag(tag, limit = 15)
            results.addAll(similar.filter { it.stationuuid != station.stationuuid })
        }
        return results.distinctBy { it.stationuuid }.shuffled().take(10)
    }

    /**
     * "Tendencias para ti" - populares en tu pais preferido.
     */
    suspend fun getTrendingForYou(): List<RadioBrowserStation> {
        val countries = getTopCountries()
        if (countries.isEmpty()) {
            return radioBrowser.getTopStations(15)
        }
        val results = mutableListOf<RadioBrowserStation>()
        for (country in countries.take(2)) {
            val stations = radioBrowser.searchByCountry(country, limit = 15)
            results.addAll(stations)
        }
        return results.distinctBy { it.stationuuid }.shuffled().take(15)
    }

    /**
     * Emisoras populares globalmente.
     */
    suspend fun getGlobalTrending(): List<RadioBrowserStation> {
        return radioBrowser.getTopStations(20)
    }

    /**
     * Generos que podrian gustarte (basado en tags escuchados).
     */
    suspend fun getSuggestedGenres(): List<String> {
        val tags = getTopTags()
        val relatedGenres = mapOf(
            "techno" to listOf("house", "trance", "minimal", "electronic"),
            "house" to listOf("deep house", "tech house", "progressive house"),
            "trance" to listOf("psytrance", "progressive trance", "goa"),
            "rock" to listOf("alternative rock", "indie rock", "classic rock"),
            "jazz" to listOf("smooth jazz", "fusion", "bossa nova"),
            "salsa" to listOf("bachata", "merengue", "latin jazz"),
            "reggaeton" to listOf("latin trap", "dembow", "urban"),
            "lofi" to listOf("chillhop", "ambient", "downtempo"),
            "hip hop" to listOf("rap", "trap", "r&b"),
            "classical" to listOf("piano", "orchestral", "chamber music")
        )

        val suggestions = mutableSetOf<String>()
        for (tag in tags) {
            relatedGenres[tag]?.let { suggestions.addAll(it) }
        }
        return suggestions.toList().shuffled().take(8)
    }

    // Favoritos
    suspend fun addFavorite(stationUuid: String) {
        dataStore.edit { prefs ->
            val current = prefs[FAVORITE_STATIONS] ?: emptySet()
            prefs[FAVORITE_STATIONS] = current + stationUuid
        }
    }

    suspend fun removeFavorite(stationUuid: String) {
        dataStore.edit { prefs ->
            val current = prefs[FAVORITE_STATIONS] ?: emptySet()
            prefs[FAVORITE_STATIONS] = current - stationUuid
        }
    }

    fun getFavorites(): Flow<Set<String>> {
        return dataStore.data.map { it[FAVORITE_STATIONS] ?: emptySet() }
    }

    suspend fun isFavorite(stationUuid: String): Boolean {
        return dataStore.data.first()[FAVORITE_STATIONS]?.contains(stationUuid) ?: false
    }

    // Helpers privados
    private suspend fun getTopTags(): List<String> {
        val tags = dataStore.data.first()[LISTENED_TAGS] ?: emptySet()
        return tags.toList().shuffled().take(5)
    }

    private suspend fun getTopCountries(): List<String> {
        return dataStore.data.first()[LISTENED_COUNTRIES]?.toList() ?: emptyList()
    }

    suspend fun getListenCount(): Int {
        return dataStore.data.first()[LISTEN_COUNT] ?: 0
    }

    suspend fun clearHistory() {
        dataStore.edit { it.clear() }
    }
}
