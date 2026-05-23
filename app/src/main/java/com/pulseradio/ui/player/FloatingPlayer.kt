package com.pulseradio.ui.player

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.pulseradio.data.RadioStation
import com.pulseradio.player.RadioBrowserStation
import com.pulseradio.viewmodel.RadioViewModel

@Composable
fun FloatingPlayer(
    viewModel: RadioViewModel,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
    isExpanded: Boolean
) {
    val currentStation by viewModel.currentStation.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val title by viewModel.currentTitle.collectAsState()
    val artist by viewModel.currentArtist.collectAsState()
    val coverUrl by viewModel.currentCoverUrl.collectAsState()

    val (stationName, stationGenre, stationLocation, stationImage) = when (currentStation) {
        is RadioStation -> {
            val s = currentStation as RadioStation
            Quadruple(s.name, s.genre, s.location, s.img)
        }
        is RadioBrowserStation -> {
            val s = currentStation as RadioBrowserStation
            Quadruple(s.name, s.genre, s.location, s.imageUrl)
        }
        else -> Quadruple("PulseRadio", "Streaming", "Global", "")
    }

    val imageUrl = coverUrl.takeIf { it.isNotBlank() } ?: stationImage

    AnimatedVisibility(
        visible = stationName != "PulseRadio" || isPlaying,
        enter = slideInVertically { it },
        exit = slideOutVertically { it }
    ) {
        if (isExpanded) {
            ExpandedPlayer(
                stationName = stationName,
                stationGenre = stationGenre,
                stationLocation = stationLocation,
                imageUrl = imageUrl,
                title = title,
                artist = artist,
                isPlaying = isPlaying,
                onPlayPause = { viewModel.togglePlay() },
                onCollapse = onCollapse,
                viewModel = viewModel
            )
        } else {
            MiniPlayer(
                stationName = stationName,
                imageUrl = imageUrl,
                isPlaying = isPlaying,
                onPlayPause = { viewModel.togglePlay() },
                onExpand = onExpand
            )
        }
    }
}

@Composable
fun MiniPlayer(
    stationName: String,
    imageUrl: String,
    isPlaying: Boolean,
    onPlayPause: () -> Unit,
    onExpand: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF1A1A1A))
            .clickable(onClick = onExpand)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = stationName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stationName,
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "En directo",
                color = Color(0xFF00D4FF),
                fontSize = 11.sp,
                maxLines = 1
            )
        }
        IconButton(onClick = onPlayPause) {
            Icon(
                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = if (isPlaying) "Pausar" else "Reproducir",
                tint = Color(0xFF00D4FF),
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun ExpandedPlayer(
    stationName: String,
    stationGenre: String,
    stationLocation: String,
    imageUrl: String,
    title: String,
    artist: String,
    isPlaying: Boolean,
    onPlayPause: () -> Unit,
    onCollapse: () -> Unit,
    viewModel: RadioViewModel
) {
    // Estado para tiempo escuchado
    var listenedSeconds by remember { mutableIntStateOf(0) }
    var isLiked by remember { mutableStateOf(false) }

    // Incrementar tiempo escuchado cada segundo si esta reproduciendo
    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            kotlinx.coroutines.delay(1000)
            listenedSeconds++
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF1A1A1A),
                        Color(0xFF0D0D0D)
                    )
                )
            )
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top row: Download, EN DIRECTO, Share
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(36.dp)
                    .background(Color(0xFF2A2A2A), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Descargar",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF2A2A2A))
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00FF88))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "EN DIRECTO",
                    color = Color(0xFF00D4FF),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            }

            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(36.dp)
                    .background(Color(0xFF2A2A2A), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Compartir",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Circular album art with vinyl effect
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(Color(0xFF2A2A2A)),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = stationName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF0D0D0D))
            )
            if (isPlaying) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                listOf(
                                    Color.Transparent,
                                    Color(0xFF00D4FF).copy(alpha = 0.1f)
                                )
                            )
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Waveform visualizer
        AudioVisualizer(isPlaying = isPlaying)

        Spacer(modifier = Modifier.height(16.dp))

        // Station name
        Text(
            text = stationName.uppercase(),
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            letterSpacing = 1.sp
        )

        // Location and tags
        Row(
            modifier = Modifier.padding(top = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = Color(0xFF00D4FF),
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = " $stationLocation",
                color = Color(0xFF00D4FF),
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "  |  $stationGenre",
                color = Color(0xFF71717a),
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ===== TIMELINE / PROGRESS BAR (exacto de la imagen oficial) =====
        Column(modifier = Modifier.fillMaxWidth()) {
            // Progress bar with gradient and thumb dot
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                // Background track
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color(0xFF2A2A2A))
                )
                // Progress fill with gradient (cyan to magenta)
                val progress = (listenedSeconds % 300) / 300f // Simula progreso ciclico
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress)
                        .height(3.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xFF00D4FF), Color(0xFFFF00FF))
                            )
                        )
                )
                // Thumb dot
                Box(
                    modifier = Modifier
                        .padding(start = (progress * 1000).dp) // Aproximado, se ajusta con fillMaxWidth
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .offset(x = (-5).dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Time row: 00:18 | VIVO | Escuchado: 59s
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left: elapsed time with dot
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF00D4FF))
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    val minutes = listenedSeconds / 60
                    val seconds = listenedSeconds % 60
                    Text(
                        text = String.format("%02d:%02d", minutes, seconds),
                        color = Color(0xFF00D4FF),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Right: VIVO + Escuchado
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "VIVO",
                        color = Color(0xFF9D4EDD), // Morado/purpura como en la imagen
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "  Escuchado: ${listenedSeconds}s",
                        color = Color(0xFFa1a1aa),
                        fontSize = 11.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Controls: Prev, Play/Pause, Next
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFF2A2A2A), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.SkipPrevious,
                    contentDescription = "Anterior",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            FilledIconButton(
                onClick = onPlayPause,
                modifier = Modifier.size(64.dp),
                shape = CircleShape,
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF0D0D0D)
                )
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pausar" else "Reproducir",
                    modifier = Modifier.size(32.dp)
                )
            }

            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFF2A2A2A), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.SkipNext,
                    contentDescription = "Siguiente",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Bottom actions: HQ AUDIO, TRANSMITIR, GUARDAR/CON CORAZON
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ActionButton(
                icon = Icons.Default.HighQuality,
                label = "HQ AUDIO",
                onClick = { }
            )
            ActionButton(
                icon = Icons.Default.Cast,
                label = "TRANSMITIR",
                onClick = {
                    // Abre el selector de dispositivos Cast
                    val castContext = viewModel.chromecastManager?.getCastContext()
                    castContext?.let {
                        // El boton Cast abre el MediaRouteChooserDialog
                        // La logica real se maneja via CastContext.getSharedInstance
                    }
                }
            )
            ActionButton(
                icon = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                label = if (isLiked) "CON CORAZON" else "GUARDAR",
                onClick = {
                    isLiked = !isLiked
                    val current = viewModel.currentStation.value
                    if (current is RadioBrowserStation) {
                        viewModel.toggleFavorite(current.stationuuid)
                    }
                },
                tint = if (isLiked) Color(0xFFFF006E) else Color(0xFF00D4FF)
            )
        }
    }
}

@Composable
fun AudioVisualizer(isPlaying: Boolean) {
    val barCount = 40
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(barCount) { index ->
            val animatedHeight by animateFloatAsState(
                targetValue = if (isPlaying) {
                    (0.2f + 0.8f * kotlin.math.abs(kotlin.math.sin(index * 0.4f + System.currentTimeMillis() / 400f))).coerceIn(0.1f, 1f)
                } else 0.1f,
                animationSpec = tween(200),
                label = "bar$index"
            )
            val color = when {
                index < barCount / 3 -> Color(0xFF00D4FF)
                index < 2 * barCount / 3 -> Color(0xFFFF00FF)
                else -> Color(0xFF00D4FF)
            }
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .fillMaxHeight(animatedHeight)
                    .clip(RoundedCornerShape(2.dp))
                    .background(color.copy(alpha = 0.8f))
            )
        }
    }
}

@Composable
fun ActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit,
    tint: Color = Color(0xFF00D4FF)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = tint,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = if (tint == Color(0xFFFF006E)) Color(0xFFFF006E) else Color(0xFFa1a1aa),
            fontSize = 9.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 1.sp
        )
    }
}

data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
