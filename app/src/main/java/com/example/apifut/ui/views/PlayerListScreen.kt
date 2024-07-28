package com.example.apifut.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.apifut.network.Player
import com.example.apifut.ui.theme.ApiFutTheme

@Composable
fun PlayerListScreen(
    playerViewModel: PlayerViewModel = viewModel()
) {
    val uiState by playerViewModel.uiState.collectAsState()
    when (uiState) {
        is PlayerUiState.Loading -> LoadingScreen()
        is PlayerUiState.Success -> PlayerList(players = (uiState as PlayerUiState.Success).players)
        is PlayerUiState.Error -> ErrorScreen()
    }
}

@Composable
fun LoadingScreen() {
    // Implementar UI de carregamento
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        Text("Loading...", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun ErrorScreen() {
    // Implementar UI de erro
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red),
        contentAlignment = Alignment.Center
    ) {
        Text("Error occurred", style = MaterialTheme.typography.bodyLarge, color = Color.White)
    }
}

@Composable
fun PlayerList(
    players: List<Player>
) {
    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxSize()
    ) {
        Text(
            text = "Player List",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(16.dp)
                .background(Color.Blue)
                .fillMaxWidth()
                .padding(8.dp),
            color = Color.White
        )
        LazyColumn {
            items(players) { player ->
                PlayerEntry(player = player)
            }
        }
    }
}

@Composable
fun PlayerEntry(
    player: Player
) {
    val imageUrl = "https://futdb.app/api/players/${player.id}/image"
    val authToken = "820c26f3-2944-4dea-8260-b5a59fa5faac"

    Card(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
    ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .addHeader("X-AUTH-TOKEN", authToken)
                    .crossfade(true)
                    .build(),
                contentDescription = player.firstName,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = player.firstName,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp),
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
            )
        }
    }
}
