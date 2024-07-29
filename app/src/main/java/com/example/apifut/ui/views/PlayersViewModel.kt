package com.example.apifut.ui.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apifut.network.FutdbApi
import com.example.apifut.network.Player
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class PlayerViewModel : ViewModel() {

    private var _uiState: MutableStateFlow<PlayerUiState> = MutableStateFlow(PlayerUiState.Loading)
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()

    init {
        getPlayers(1, 10)
    }

    private fun getPlayers(startId: Int, endId: Int) {
        val authToken = "820c26f3-2944-4dea-8260-b5a59fa5faac"
        viewModelScope.launch {
            try {
                val players = mutableListOf<Player>()
                val requests = (startId..endId).map { playerId ->
                    async {
                        try {
                            FutdbApi.retrofitService.getPlayer(playerId, authToken).player
                        } catch (e: IOException) {
                            null
                        } catch (e: HttpException) {
                            null
                        }
                    }
                }
                requests.forEach { request ->
                    val player = request.await()
                    player?.let { players.add(it) }
                }
                _uiState.value = PlayerUiState.Success(players)
            } catch (e: Exception) {
                _uiState.value = PlayerUiState.Error
            }
        }
    }

    private val _playerDetailState = MutableStateFlow<PlayerDetailState>(PlayerDetailState.Loading)
    val playerDetailState: StateFlow<PlayerDetailState> = _playerDetailState.asStateFlow()

    fun getPlayer(playerId: String) {
        val authToken = "820c26f3-2944-4dea-8260-b5a59fa5faac"
        viewModelScope.launch {
            _playerDetailState.value = PlayerDetailState.Loading
            try {
                val player = FutdbApi.retrofitService.getPlayer(playerId.toInt(), authToken).player
                _playerDetailState.value = PlayerDetailState.Success(player)
            } catch (e: IOException) {
                _playerDetailState.value = PlayerDetailState.Error
            } catch (e: HttpException) {
                _playerDetailState.value = PlayerDetailState.Error
            }
        }
    }
}

sealed interface PlayerUiState {
    object Loading : PlayerUiState
    data class Success(val players: List<Player>) : PlayerUiState
    object Error : PlayerUiState
}

sealed interface PlayerDetailState {
    object Loading : PlayerDetailState
    data class Success(val player: Player) : PlayerDetailState
    object Error : PlayerDetailState
}
