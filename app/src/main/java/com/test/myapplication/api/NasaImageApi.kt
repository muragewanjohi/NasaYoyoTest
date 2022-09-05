package com.test.myapplication.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaImageApi {

    companion object {
      //  const val BASE_URL = "https://images-api.nasa.gov/"
        const val NASA_BASE_URL = "https://images-api.nasa.gov/"
    }

    @GET("search?q=milky%20way&media_type=image&year_start=2017&year_end=2017")
    suspend fun searchPhotos(
        @Query("q") q: String,
        @Query("media_type") media_type: String,
        @Query("year_start") year_start: String,
        @Query("year_end") year_end: String,
        @Query("page") page: Int
    ) : Response<NasaApiObject>
}