package com.mostafa.whatnow

import retrofit2.Call
import retrofit2.http.GET

interface NewsCallable {
    // the request from the server to get the data
    @GET("/v2/top-headlines?country=de&category=business&apiKey=be8e557ae5e1410abbe0981d7af80d13&pageSize=30")

    // sending the data to dataClass to save the data in the variables
    fun getNews() : Call<News>

}