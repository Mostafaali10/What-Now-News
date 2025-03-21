package com.mostafa.whatnow.Activitys

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.mostafa.whatnow.NewsApi.Article
import com.mostafa.whatnow.NewsApi.News
import com.mostafa.whatnow.NewsApi.NewsCallable
import com.mostafa.whatnow.databinding.RecycleViewListHolderBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: RecycleViewListHolderBinding
    private lateinit var sharedPreferences : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = RecycleViewListHolderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val category = intent.getStringExtra("category")
        sharedPreferences = getSharedPreferences("NewsPrefs", MODE_PRIVATE)
        val country = sharedPreferences.getString("selected_country", "us") ?: "us"
        Log.d("preference", country)

        binding.toolbarBackBtn.setOnClickListener {
            val i = Intent(this,HomeScreenActivity::class.java)
            startActivity(i)
            finish()
        }
        binding.toolbarHeaderTv.text = category?.uppercase() ?: "GENERAL"


        loadNews(category = category, country = country)

    }


    private fun loadNews(
        category: String?,
        country: String
    ) {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val c = retrofit.create(NewsCallable::class.java)
        c.getGeneralNews(
            category = category,
            country = country
        ).enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if (response.isSuccessful) {
                    val news = response.body()
                    val articles = news?.articles!!
                    articles.removeAll {
                        it.title == "[Removed]"
                    }
                    Log.d("trace", "Articles: $articles")
                    showNews(articles)
                    binding.progress.isVisible = false
                    binding.swipeRefresh.isRefreshing = false
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("trace", "Error :${t.message}")
                val articles = ArrayList<Article>()
                articles.add(Article(title = "Title"))
                showNews(articles)
                binding.progress.isVisible = false
                binding.swipeRefresh.isRefreshing = false
            }

        })
    }

    fun showNews(articles: ArrayList<Article>) {
        val adapter = NewsAdapter(this, articles)
        binding.newsList.adapter = adapter
    }

}