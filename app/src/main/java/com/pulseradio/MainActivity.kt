package com.pulseradio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pulseradio.data.RadioStation
import com.pulseradio.player.RadioBrowserStation
import com.pulseradio.ui.home.HomeScreen
import com.pulseradio.ui.navigation.PulseBottomNavigation
import com.pulseradio.ui.player.FloatingPlayer
import com.pulseradio.ui.theme.PulseRadioTheme
import com.pulseradio.viewmodel.RadioViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PulseRadioTheme {
                val viewModel: RadioViewModel = viewModel()
                var currentRoute by remember { mutableStateOf("home") }
                var playerExpanded by remember { mutableStateOf(false) }
                var showPlayer by remember { mutableStateOf(false) }

                Scaffold(
                    bottomBar = {
                        if (!playerExpanded) {
                            Column {
                                // Floating mini player above bottom nav
                                FloatingPlayer(
                                    viewModel = viewModel,
                                    onExpand = {
                                        playerExpanded = true
                                        showPlayer = true
                                    },
                                    onCollapse = {
                                        playerExpanded = false
                                        showPlayer = false
                                    },
                                    isExpanded = false
                                )
                                PulseBottomNavigation(
                                    currentRoute = currentRoute,
                                    onNavigate = { currentRoute = it }
                                )
                            }
                        }
                    }
                ) { padding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        when (currentRoute) {
                            "home" -> HomeScreen(
                                viewModel = viewModel,
                                onPlayLocalStation = { station ->
                                    viewModel.playLocalStation(station)
                                    showPlayer = true
                                },
                                onPlayBrowserStation = { station ->
                                    viewModel.playBrowserStation(station)
                                    showPlayer = true
                                },
                                onOpenPlayer = {
                                    playerExpanded = true
                                    showPlayer = true
                                }
                            )
                            "discover" -> DiscoverPlaceholder()
                            "favorites" -> com.pulseradio.ui.favorites.FavoritesScreen(
                                viewModel = viewModel,
                                onPlayLocalStation = { station ->
                                    viewModel.playLocalStation(station)
                                    showPlayer = true
                                },
                                onOpenPlayer = {
                                    playerExpanded = true
                                    showPlayer = true
                                }
                            )
                            "settings" -> SettingsPlaceholder()
                        }

                        // Expanded floating player overlay
                        if (playerExpanded && showPlayer) {
                            FloatingPlayer(
                                viewModel = viewModel,
                                onExpand = { },
                                onCollapse = {
                                    playerExpanded = false
                                    showPlayer = false
                                },
                                isExpanded = true
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DiscoverPlaceholder() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
        androidx.compose.material3.Text("Descubrir - Proximamente", color = androidx.compose.ui.graphics.Color(0xFF71717a))
    }
}

@Composable
fun SettingsPlaceholder() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
        androidx.compose.material3.Text("Ajustes - Proximamente", color = androidx.compose.ui.graphics.Color(0xFF71717a))
    }
}
