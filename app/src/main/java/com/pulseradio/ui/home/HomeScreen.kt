package com.pulseradio.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.pulseradio.data.RadioStation
import com.pulseradio.data.StationCategory
import com.pulseradio.data.StationDatabase
import com.pulseradio.player.RadioBrowserStation
import com.pulseradio.viewmodel.RadioViewModel

@Composable
fun HomeScreen(
    viewModel: RadioViewModel,
    onPlayLocalStation: (RadioStation) -> Unit,
    onPlayBrowserStation: (RadioBrowserStation) -> Unit,
    onOpenPlayer: () -> Unit
) {
    val searchResults by viewModel.searchResults.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    var searchText by remember { mutableStateOf("") }

    // Recomendaciones
    val madeForYou by viewModel.madeForYou.collectAsState()
    val weeklyDiscovery by viewModel.weeklyDiscovery.collectAsState()
    val trendingForYou by viewModel.trendingForYou.collectAsState()
    val globalTrending by viewModel.globalTrending.collectAsState()
    val suggestedGenres by viewModel.suggestedGenres.collectAsState()
    val isLoadingRecs by viewModel.isLoadingRecs.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D0D0D))
            .padding(horizontal = 20.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(20.dp))
            HeaderSection()
            Spacer(modifier = Modifier.height(20.dp))
            GreetingSection(name = "ALEJANDRO.")
            Spacer(modifier = Modifier.height(20.dp))
            SearchBar(
                query = searchText,
                onQueryChange = { searchText = it },
                onSearch = { viewModel.searchGlobal(searchText) },
                onClear = {
                    searchText = ""
                    viewModel.clearSearch()
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Show search results if active
        if (searchQuery.isNotBlank() || searchResults.isNotEmpty()) {
            item {
                if (isSearching) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFF00D4FF))
                    }
                } else if (searchResults.isEmpty() && searchQuery.isNotBlank()) {
                    Text(
                        text = "No se encontraron emisoras para '$searchQuery'",
                        color = Color(0xFF71717a),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                } else {
                    Text(
                        text = "${searchResults.size} emisoras encontradas",
                        color = Color(0xFF00D4FF),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
            }
            items(searchResults) { station ->
                BrowserStationCard(
                    station = station,
                    onClick = {
                        onPlayBrowserStation(station)
                        onOpenPlayer()
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        } else {
            // RECOMENDACIONES ESTILO SPOTIFY/TUNEIN

            // 1. HECHO PARA TI
            if (madeForYou.isNotEmpty()) {
                item {
                    SectionHeader(title = "HECHO PARA TI", action = "")
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(madeForYou.take(10)) { station ->
                            RecommendationCard(
                                station = station,
                                onClick = {
                                    onPlayBrowserStation(station)
                                    onOpenPlayer()
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(28.dp))
                }
            }

            // 2. DESCUBRIMIENTO SEMANAL
            if (weeklyDiscovery.isNotEmpty()) {
                item {
                    SectionHeader(title = "DESCUBRIMIENTO SEMANAL", action = "")
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(weeklyDiscovery.take(10)) { station ->
                            DiscoveryCard(
                                station = station,
                                onClick = {
                                    onPlayBrowserStation(station)
                                    onOpenPlayer()
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(28.dp))
                }
            }

            // 3. TENDENCIAS PARA TI
            if (trendingForYou.isNotEmpty()) {
                item {
                    SectionHeader(title = "TENDENCIAS PARA TI", action = "")
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(trendingForYou.take(10)) { station ->
                            TrendingForYouCard(
                                station = station,
                                onClick = {
                                    onPlayBrowserStation(station)
                                    onOpenPlayer()
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(28.dp))
                }
            }

            // 4. POPULARES GLOBALMENTE
            if (globalTrending.isNotEmpty()) {
                item {
                    SectionHeader(title = "POPULARES GLOBALMENTE", action = "")
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(globalTrending.take(10)) { station ->
                            GlobalTrendingCard(
                                station = station,
                                onClick = {
                                    onPlayBrowserStation(station)
                                    onOpenPlayer()
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(28.dp))
                }
            }

            // 5. GENEROS QUE PODRIAN GUSTARTE
            if (suggestedGenres.isNotEmpty()) {
                item {
                    SectionHeader(title = "GENEROS QUE PODRIAN GUSTARTE", action = "")
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(suggestedGenres) { genre ->
                            GenreChip(
                                genre = genre,
                                onClick = {
                                    viewModel.searchGlobal(genre)
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(28.dp))
                }
            }

            // 6. TENDENCIAS GLOBALES (locales)
            item {
                SectionHeader(title = "TENDENCIAS GLOBALES", action = "DESLIZAR →")
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(StationDatabase.allStations.take(5).mapIndexed { index, station ->
                        TrendingItem(
                            rank = index + 1,
                            station = station,
                            listeners = "${(20 + index * 15)}.${index}K"
                        )
                    }) { item ->
                        TrendingCard(
                            item = item,
                            onClick = {
                                onPlayLocalStation(item.station)
                                onOpenPlayer()
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(28.dp))
            }

            // 7. GENEROS ELECTRONICOS
            item {
                SectionHeader(title = "GENEROS ELECTRONICOS", action = "")
                Spacer(modifier = Modifier.height(12.dp))
            }
            items(StationDatabase.categories.firstOrNull { it.id == "electronic" }?.stations?.take(6) ?: emptyList()) { station ->
                GenreStationCard(
                    station = station,
                    onClick = {
                        onPlayLocalStation(station)
                        onOpenPlayer()
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            // 8. Mas categorias locales
            items(StationDatabase.categories.drop(1)) { category ->
                Spacer(modifier = Modifier.height(16.dp))
                SectionHeader(title = category.name.uppercase(), action = "")
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(category.stations.take(6)) { station ->
                        CompactStationCard(station, onClick = {
                            onPlayLocalStation(station)
                            onOpenPlayer()
                        })
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

// === NUEVAS TARJETAS DE RECOMENDACION ===

@Composable
fun RecommendationCard(station: RadioBrowserStation, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(160.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF1A1A1A))
            .clickable(onClick = onClick)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = station.imageUrl,
            contentDescription = station.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = station.name,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Text(
            text = "${station.genre} • ${station.country}",
            color = Color(0xFF71717a),
            fontSize = 9.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun DiscoveryCard(station: RadioBrowserStation, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(200.dp)
            .height(200.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = station.imageUrl,
            contentDescription = station.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color(0xFF0D0D0D).copy(alpha = 0.8f)
                        )
                    )
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFFFF00FF))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "NUEVO",
                    color = Color.White,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = station.name,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${station.genre} • ${station.country}",
                color = Color(0xFF00D4FF),
                fontSize = 10.sp,
                maxLines = 1
            )
        }
    }
}

@Composable
fun TrendingForYouCard(station: RadioBrowserStation, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .width(280.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF1A1A1A))
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = station.imageUrl,
            contentDescription = station.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = station.name,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${station.country} • ${station.clickcount} oyentes",
                color = Color(0xFF00D4FF),
                fontSize = 10.sp
            )
            Text(
                text = station.genre,
                color = Color(0xFF71717a),
                fontSize = 9.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun GlobalTrendingCard(station: RadioBrowserStation, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(140.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF1A1A1A), Color(0xFF0D0D0D))
                )
            )
            .clickable(onClick = onClick)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = station.imageUrl,
            contentDescription = station.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = station.name,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.TrendingUp,
                contentDescription = null,
                tint = Color(0xFF00FF88),
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = " ${station.clickcount}",
                color = Color(0xFF00FF88),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun GenreChip(genre: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF2A2A2A))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(
            text = genre.uppercase(),
            color = Color(0xFF00D4FF),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
    }
}

// === COMPONENTES EXISTENTES (mantenidos) ===

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF00D4FF))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                SoundWaveIcon()
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "PULSERADIO",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "PREMIUM AUDIO EXPERIENCE",
                    color = Color(0xFF71717a),
                    fontSize = 8.sp,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFF1A1A1A))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF00FF88))
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "LIVE",
                color = Color(0xFF00FF88),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }
    }
}

@Composable
fun SoundWaveIcon() {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val heights = listOf(0.4f, 0.7f, 1f, 0.7f, 0.4f)
        heights.forEach { height ->
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .fillMaxHeight(height)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color(0xFF0D0D0D))
            )
        }
    }
}

@Composable
fun GreetingSection(name: String) {
    Text(
        text = "BUENOS DÍAS,",
        color = Color.White,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 32.sp
    )
    Text(
        text = name,
        color = Color.White,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 32.sp
    )
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onClear: () -> Unit
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = {
            Text(
                "BUSCAR EMISORA, GÉNERO O TAG...",
                color = Color(0xFF71717a),
                fontSize = 13.sp,
                letterSpacing = 1.sp
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar",
                tint = Color(0xFF71717a),
                modifier = Modifier.size(20.dp)
            )
        },
        trailingIcon = {
            if (query.isNotBlank()) {
                IconButton(onClick = onClear) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Limpiar",
                        tint = Color(0xFF71717a),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearch() }),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFF1A1A1A),
            unfocusedContainerColor = Color(0xFF1A1A1A),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color(0xFF00D4FF)
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun SectionHeader(title: String, action: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = Color(0xFF00D4FF),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )
        if (action.isNotBlank()) {
            Text(
                text = action,
                color = Color(0xFF71717a),
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.sp
            )
        }
    }
}

data class TrendingItem(
    val rank: Int,
    val station: RadioStation,
    val listeners: String
)

@Composable
fun TrendingCard(item: TrendingItem, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(280.dp)
            .height(160.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = item.station.img,
            contentDescription = item.station.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0xFF0D0D0D).copy(alpha = 0.7f),
                            Color(0xFF0D0D0D).copy(alpha = 0.95f)
                        ),
                        startY = 0f,
                        endY = 500f
                    )
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFF00D4FF))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "TENDENCIA #${item.rank}",
                        color = Color(0xFF0D0D0D),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xCC000000))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = item.listeners,
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.station.genre.uppercase(),
                        color = Color(0xFF00D4FF),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = item.station.name,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = item.station.location,
                        color = Color(0xFFa1a1aa),
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable(onClick = onClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = Color(0xFF0D0D0D),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun GenreStationCard(station: RadioStation, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF1A1A1A))
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(44.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF00D4FF).copy(alpha = 0.15f))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                SoundWaveIconSmall()
            }
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF00D4FF))
                    .align(Alignment.TopEnd)
            )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = station.name.uppercase(),
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = station.genre.uppercase(),
                color = Color(0xFF71717a),
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.White)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = Color(0xFF0D0D0D),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun SoundWaveIconSmall() {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val heights = listOf(0.4f, 0.7f, 1f, 0.7f, 0.4f)
        heights.forEach { height ->
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .fillMaxHeight(height)
                    .clip(RoundedCornerShape(1.dp))
                    .background(Color(0xFF00D4FF))
            )
        }
    }
}

@Composable
fun CompactStationCard(station: RadioStation, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(140.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF1A1A1A))
            .clickable(onClick = onClick)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = station.img,
            contentDescription = station.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = station.name,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Text(
            text = station.genre,
            color = Color(0xFF00D4FF),
            fontSize = 9.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun BrowserStationCard(
    station: RadioBrowserStation,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF1A1A1A))
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = station.imageUrl,
            contentDescription = station.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = station.name,
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = station.genre,
                color = Color(0xFF00D4FF),
                fontSize = 10.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${station.country} • ${station.bitrate}kbps • ${station.clickcount} clicks",
                color = Color(0xFFa1a1aa),
                fontSize = 9.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color(0xFF00D4FF))
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = Color(0xFF0D0D0D),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
