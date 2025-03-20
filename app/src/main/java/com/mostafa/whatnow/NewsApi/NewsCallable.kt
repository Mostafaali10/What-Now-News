package com.mostafa.whatnow.NewsApi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsCallable {
    // the request from the server to get the data
    @GET("/v2/top-headlines?apiKey=1baed4752b0c491bb37ccc2723f1a76a")
    fun getGeneralNews(
        @Query("country") country: String,
        @Query("category") category: String?
    ): Call<News>

}