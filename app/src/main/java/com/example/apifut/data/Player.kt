package com.example.apifut.data

import com.squareup.moshi.Json

data class Player(
    @Json(name = "id") val id: Int,
    @Json(name = "firstName") val firstName: String,
    @Json(name = "lastName") val lastName: String,
    @Json(name = "name") val name: String,
    @Json(name = "commonName") val commonName: String?,
    @Json(name = "rating") val rating: Int,
    @Json(name = "skillMoves") val skillMoves: Int,
    @Json(name = "weakFoot") val weakFoot: Int,
    @Json(name = "foot") val foot: String,
    @Json(name = "attackWorkRate") val attackWorkRate: String,
    @Json(name = "defenseWorkRate") val defenseWorkRate: String,
    @Json(name = "pace") val pace: Int,
    @Json(name = "shooting") val shooting: Int,
    @Json(name = "passing") val passing: Int,
    @Json(name = "dribbling") val dribbling: Int,
    @Json(name = "defending") val defending: Int,
    @Json(name = "physicality") val physicality: Int
)

data class PlayerResponse(
    @Json(name = "player") val player: Player
)
