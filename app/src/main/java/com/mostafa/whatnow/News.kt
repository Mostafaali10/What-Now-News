package com.mostafa.whatnow

// data class will receive the data in these variables

data class News(
    val articles : ArrayList<Article>
)

data class Article(
    val title : String ,
    val url : String ,
    val urlToImage: String
)
