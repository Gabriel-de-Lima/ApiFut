package com.example.apifut.data

import com.squareup.moshi.Json

data class Player(
    @Json(name = "id") val id: Int,
    @Json(name = "firstName") val firstName: String,
    @Json(name = "lastName") val lastName: String,
    @Json(name = "nation") val nation: Int,
    @Json(name = "league") val league: Int,
    @Json(name = "club") val club: Int,
    @Json(name = "position") val position: String,
    @Json(name = "rating") val rating: Int
)


data class PlayerResponse(
    @Json(name = "player") val player: Player
)
