package com.example.apifut.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

// Constantes
private const val BASE_URL = "https://futdb.app/api/"

// Configuração do Moshi
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Configuração do Retrofit
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

// Interface do serviço Retrofit
interface FutdbApiService {
    @GET("players/{id}")
    suspend fun getPlayer(
        @Path("id") playerId: Int,
        @Header("X-AUTH-TOKEN") authToken: String
    ): PlayerResponse
}

// Objeto de API para acessar o serviço Retrofit
object FutdbApi {
    val retrofitService: FutdbApiService by lazy {
        retrofit.create(FutdbApiService::class.java)
    }
}

// Classes de dados para a resposta
data class Player(
    val id: Int, // Adicionando o campo id
    val firstName: String,
    val lastName: String,
    val nation: Int,
    val league: Int,
    val club: Int,
    val position: String,
    val rating: Int
)

data class PlayerResponse(
    val player: Player
)
