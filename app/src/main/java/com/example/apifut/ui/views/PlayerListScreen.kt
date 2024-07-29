package com.example.apifut.ui.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.apifut.R
import com.example.apifut.network.Player

@Composable
fun PlayerApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "playerList") {
        composable("playerList") {
            PlayerListScreen(navController)
        }
        composable("playerDetail/{playerId}") { backStackEntry ->
            val playerId = backStackEntry.arguments?.getString("playerId")
            PlayerDetailScreen(playerId = playerId ?: "", navController = navController)
        }
    }
}




@Composable
fun PlayerListScreen(
    navController: NavHostController,
    playerViewModel: PlayerViewModel = viewModel()
) {
    val uiState by playerViewModel.uiState.collectAsState()
    when (uiState) {
        is PlayerUiState.Loading -> LoadingScreen()
        is PlayerUiState.Success -> PlayerList(
            players = (uiState as PlayerUiState.Success).players,
            navController = navController
        )
        is PlayerUiState.Error -> ErrorScreen()
    }
}
@Composable
fun LoadingScreen() {
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
    players: List<Player>,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .background(Color(218, 250, 218))
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0, 64, 0))
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_app),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(50.dp)
                    .padding(top = 3.dp, bottom = 3.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Player List FC 24",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        LazyColumn {
            items(players) { player ->
                PlayerEntry(player = player, navController = navController)
            }
        }
    }
}

@Composable
fun PlayerEntry(
    player: Player,
    navController: NavHostController
) {
    val imageUrl = "https://futdb.app/api/players/${player.id}/image"
    val authToken = "820c26f3-2944-4dea-8260-b5a59fa5faac"

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color(0, 64, 0),
                RoundedCornerShape(corner = CornerSize(15.dp)))
            .clickable {
                Log.d("PlayerEntry", "Navigating to player details for ID: ${player.id}")
                navController.navigate("playerDetail/${player.id}") }
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .background(Color.White)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .addHeader("X-AUTH-TOKEN", authToken)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder_image),
                contentDescription = player.firstName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RectangleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = player.commonName ?: player.name, // Exibe commonName ou name se commonName for nulo
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color(0, 64, 0),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "Rating: ${player.rating}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.DarkGray,
                        fontSize = 18.sp
                    )
                )
            }
        }
    }
}





@Composable
fun PlayerDetailScreen(
    playerId: String,
    playerViewModel: PlayerViewModel = viewModel(),
    navController: NavHostController
) {
    val playerDetailState by playerViewModel.playerDetailState.collectAsState()

    LaunchedEffect(playerId) {
        playerViewModel.getPlayer(playerId)
    }

    when (playerDetailState) {
        is PlayerDetailState.Loading -> LoadingScreen()
        is PlayerDetailState.Success -> {
            val player = (playerDetailState as PlayerDetailState.Success).player
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Top Section: Image and Basic Info
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Image
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://futdb.app/api/players/${player.id}/image")
                            .addHeader("X-AUTH-TOKEN", "820c26f3-2944-4dea-8260-b5a59fa5faac")
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.placeholder_image),
                        contentDescription = player.firstName,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(128.dp)
                            .clip(RectangleShape)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    // Basic Info
                    Column {
                        Text(text = "Name: ${player.name}", style = MaterialTheme.typography.bodyLarge)
                        Text(text = "Rating: ${player.rating}", style = MaterialTheme.typography.bodyLarge)

                        // Skill Moves with Stars
                        Text(text = "Skill Moves:", style = MaterialTheme.typography.bodyLarge)
                        SkillStars(player.skillMoves)

                        // Weak Foot with Stars
                        Text(text = "Weak Foot:", style = MaterialTheme.typography.bodyLarge)
                        SkillStars(player.weakFoot)
                    }
                }

                // Attributes Section: Pace, Shooting, Passing | Dribbling, Defending, Physicality
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Pace: ${player.pace}", style = MaterialTheme.typography.bodyLarge)
                        Text(text = "Shooting: ${player.shooting}", style = MaterialTheme.typography.bodyLarge)
                        Text(text = "Passing: ${player.passing}", style = MaterialTheme.typography.bodyLarge)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Dribbling: ${player.dribbling}", style = MaterialTheme.typography.bodyLarge)
                        Text(text = "Defending: ${player.defending}", style = MaterialTheme.typography.bodyLarge)
                        Text(text = "Physicality: ${player.physicality}", style = MaterialTheme.typography.bodyLarge)
                    }
                }

                // Attack Work Rate and Defense Work Rate
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Text(text = "Attack Work Rate: ${player.attackWorkRate}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Defense Work Rate: ${player.defenseWorkRate}", style = MaterialTheme.typography.bodyLarge)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Back Button with Restart
                Button(onClick = {
                    Log.d("PlayerDetailScreen", "Navigating back to player list")
                    navController.popBackStack("playerList", inclusive = false)
                }) {
                    Text("Back to List")
                }
            }
        }
        is PlayerDetailState.Error -> ErrorScreen()
    }
}


@Composable
fun SkillStars(rating: Int) {
    Row {
        for (i in 1..5) {
            val starColor = if (i <= rating) Color.Yellow else Color.Gray
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = starColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

