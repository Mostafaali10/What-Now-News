package com.mostafa.whatnow.NewsApi

import com.google.gson.annotations.SerializedName

// data class will receive the data in these variables

data class News(
    val articles: ArrayList<Article>
)

data class Article(
    val title: String = "",
    val url: String = "",
    @SerializedName("urlToImage")
    val imageUrl: String = "",
)


data class FavArticle(
    val title: String = "",
    val url: String = ""
)

