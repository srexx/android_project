package com.pulseradio.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pulseradio.data.RadioStation
import com.pulseradio.data.StationDatabase
import com.pulseradio.player.RadioBrowserClient
import com.pulseradio.player.RadioBrowserStation
import com.pulseradio.player.RadioPlayer
import com.pulseradio.player.RecommendationEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RadioViewModel(application: Application) : AndroidViewModel(application) {
    private val player = RadioPlayer(application.applicationContext)
    private val radioBrowser = RadioBrowserClient()
    private val recommendations = RecommendationEngine(application.applicationContext)
    val chromecastManager = ChromecastManager(application.applicationContext)

    // Estado de reproduccion
    private val _currentStation = MutableStateFlow<Any>(StationDatabase.allStations.first())
    val currentStation: StateFlow<Any> = _currentStation

    val isPlaying: StateFlow<Boolean> = player.isPlaying
    val currentTitle: StateFlow<String> = player.currentTitle
    val currentArtist: StateFlow<String> = player.currentArtist
    val currentCoverUrl: StateFlow<String> = player.currentCoverUrl

    // Categorias locales
    val categories = StationDatabase.categories

    // Resultados de busqueda
    private val _searchResults = MutableStateFlow<List<RadioBrowserStation>>(emptyList())
    val searchResults: StateFlow<List<RadioBrowserStation>> = _searchResults

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    // Recomendaciones
    private val _madeForYou = MutableStateFlow<List<RadioBrowserStation>>(emptyList())
    val madeForYou: StateFlow<List<RadioBrowserStation>> = _madeForYou

    private val _weeklyDiscovery = MutableStateFlow<List<RadioBrowserStation>>(emptyList())
    val weeklyDiscovery: StateFlow<List<RadioBrowserStation>> = _weeklyDiscovery

    private val _trendingForYou = MutableStateFlow<List<RadioBrowserStation>>(emptyList())
    val trendingForYou: StateFlow<List<RadioBrowserStation>> = _trendingForYou

    private val _globalTrending = MutableStateFlow<List<RadioBrowserStation>>(emptyList())
    val globalTrending: StateFlow<List<RadioBrowserStation>> = _globalTrending

    private val _suggestedGenres = MutableStateFlow<List<String>>(emptyList())
    val suggestedGenres: StateFlow<List<String>> = _suggestedGenres

    private val _isLoadingRecs = MutableStateFlow(false)
    val isLoadingRecs: StateFlow<Boolean> = _isLoadingRecs

    private val _favorites = MutableStateFlow<Set<String>>(emptySet())
    val favorites: StateFlow<Set<String>> = _favorites

    init {
        // Cargar favoritos y recomendaciones al iniciar
        viewModelScope.launch {
            recommendations.getFavorites().collect {
                _favorites.value = it
            }
        }
        loadRecommendations()
    }

    fun loadRecommendations() {
        _isLoadingRecs.value = true
        viewModelScope.launch {
            _madeForYou.value = recommendations.getMadeForYou()
            _weeklyDiscovery.value = recommendations.getWeeklyDiscovery()
            _trendingForYou.value = recommendations.getTrendingForYou()
            _globalTrending.value = recommendations.getGlobalTrending()
            _suggestedGenres.value = recommendations.getSuggestedGenres()
            _isLoadingRecs.value = false
        }
    }

    fun playLocalStation(station: RadioStation) {
        _currentStation.value = station
        player.play(station.streamUrl)
        viewModelScope.launch {
            recommendations.recordLocalListen(station)
        }
    }

    fun playBrowserStation(station: RadioBrowserStation) {
        _currentStation.value = station
        player.play(station.streamUrl)
        viewModelScope.launch {
            recommendations.recordListen(station)
            radioBrowser.sendClick(station.stationuuid)
        }
    }

    fun togglePlay() {
        if (isPlaying.value) {
            player.pause()
        } else {
            val current = _currentStation.value
            when (current) {
                is RadioStation -> player.play(current.streamUrl)
                is RadioBrowserStation -> player.play(current.streamUrl)
            }
        }
    }

    fun searchGlobal(query: String) {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            return
        }
        _searchQuery.value = query
        _isSearching.value = true
        viewModelScope.launch {
            val results = radioBrowser.searchStations(query, limit = 50)
            _searchResults.value = results
            _isSearching.value = false
        }
    }

    fun clearSearch() {
        _searchQuery.value = ""
        _searchResults.value = emptyList()
    }

    // Favoritos
    fun toggleFavorite(stationUuid: String) {
        viewModelScope.launch {
            if (recommendations.isFavorite(stationUuid)) {
                recommendations.removeFavorite(stationUuid)
            } else {
                recommendations.addFavorite(stationUuid)
            }
        }
    }

    fun isFavorite(stationUuid: String): Boolean {
        return _favorites.value.contains(stationUuid)
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}
