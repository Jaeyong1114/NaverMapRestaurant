package com.example.navermaprestaurant

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("v1/search/local.json") //TODO v1부터로바꿔야함
    fun getRestaurant(
        @Query("query") query: String,
        @Query("display") display: Int,

    ): Call<SearchResult>
}