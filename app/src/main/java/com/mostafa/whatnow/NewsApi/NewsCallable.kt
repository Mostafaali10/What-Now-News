package com.mostafa.whatnow.NewsApi

import retrofit2.Call
import retrofit2.http.GET

interface NewsCallable {
    // the request from the server to get the data
    @GET("/v2/top-headlines?country=us&category=general&apiKey=1baed4752b0c491bb37ccc2723f1a76a")

    // sending the data to dataClass to save the data in the variables
    fun getNews() : Call<News>

}