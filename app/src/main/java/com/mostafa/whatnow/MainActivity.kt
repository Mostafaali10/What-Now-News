package com.mostafa.whatnow

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.mostafa.whatnow.databinding.ActivityMainBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        loadNews()
        binding.swipeRefresh.setOnRefreshListener { loadNews() }

    }
    
    fun loadNews() {

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
        c.getNews().enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if (response.isSuccessful) {
                    val news = response.body()
                    val articles = news?.articles!!
                    articles.removeAll{
                        it.title == "[Removed]"
                    }
                    Log.d("trace", "Articles: $articles")
                    showNews(articles)
                    binding.progress.isVisible = false
                    binding.swipeRefresh.isRefreshing = false
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("trace" , "Error :${t.message}" )
                binding.progress.isVisible = false
                binding.swipeRefresh.isRefreshing = false
            }

        })
    }

    fun showNews(articles : ArrayList<Article>){
        val adapter = NewsAdapter(this, articles)
       binding.newsList.adapter = adapter

    }
}