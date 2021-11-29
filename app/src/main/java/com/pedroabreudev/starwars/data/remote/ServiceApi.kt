package com.pedroabreudev.starwars.data.remote

import com.pedroabreudev.starwars.data.model.character.CharacterModelData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {

    @GET("people")
    suspend fun getPeople(
        @Query("search") query: String = ""
    ): Response<CharacterModelData>
}