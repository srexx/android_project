package com.pulseradio.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PulseBottomNavigation(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        BottomNavItem("home", "INICIO", Icons.Default.Home),
        BottomNavItem("discover", "DESCUBRIR", Icons.Default.Search),
        BottomNavItem("favorites", "FAVORITOS", Icons.Default.Favorite),
        BottomNavItem("settings", "AJUSTES", Icons.Default.Settings)
    )

    NavigationBar(
        containerColor = Color(0xFF0D0D0D),
        tonalElevation = 0.dp,
        modifier = Modifier.height(64.dp)
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.route
            val color = if (selected) Color(0xFF00D4FF) else Color(0xFF71717a)

            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(item.route) },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = color,
                            modifier = Modifier.size(22.dp)
                        )
                        Text(
                            text = item.label,
                            color = color,
                            fontSize = 10.sp,
                            fontWeight = if (selected) androidx.compose.ui.text.font.FontWeight.Bold else androidx.compose.ui.text.font.FontWeight.Normal,
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF00D4FF),
                    unselectedIconColor = Color(0xFF71717a),
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)
