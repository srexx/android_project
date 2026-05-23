package com.pulseradio.ui.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun PlayerScreen(
    viewModel: RadioViewModel,
    onClose: () -> Unit
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D0D0D))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onClose,
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFF1A1A1A), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Cerrar",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            // LIVE badge
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

            Spacer(modifier = Modifier.width(40.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Cover image
        AsyncImage(
            model = imageUrl,
            contentDescription = "Cover",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(24.dp))
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Station name
        Text(
            text = stationName,
            color = Color(0xFFa1a1aa),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Song title
        if (title.isNotBlank()) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        // Artist
        if (artist.isNotBlank()) {
            Text(
                text = artist,
                color = Color(0xFF00D4FF),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 4.dp, horizontal = 16.dp)
            )
        }

        // Additional info
        Text(
            text = "$stationGenre • $stationLocation",
            color = Color(0xFF71717a),
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )

        if (title.isBlank() && artist.isBlank()) {
            Text(
                text = "Esperando metadatos del stream...",
                color = Color(0xFF71717a),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Play/Pause button
        FilledIconButton(
            onClick = { viewModel.togglePlay() },
            modifier = Modifier.size(72.dp),
            shape = CircleShape,
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = Color.White,
                contentColor = Color(0xFF0D0D0D)
            )
        ) {
            Icon(
                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = if (isPlaying) "Pausar" else "Reproducir",
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
