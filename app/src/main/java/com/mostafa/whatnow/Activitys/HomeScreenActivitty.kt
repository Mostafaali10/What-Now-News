package com.mostafa.whatnow.Activitys

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mostafa.whatnow.Auth.LoginActivity
import com.mostafa.whatnow.NewsApi.Article
import com.mostafa.whatnow.NewsApi.News
import com.mostafa.whatnow.NewsApi.NewsCallable
import com.mostafa.whatnow.R
import com.mostafa.whatnow.databinding.ActivityHomeScreenBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.homeScreen)

        loadNews()


        // Set up the toolbar
        setSupportActionBar(binding.toolbar)
        favoriteArticleButton()
        generalButton()
        healthBtn()
        scienceBtn()
        sportBtn()
        technologyBtb()
        businessBtn()
    }


    // Inflate the menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // Handle menu item clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val auth: FirebaseAuth = Firebase.auth
        return when (item.itemId) {
            R.id.action_logout -> {
                auth.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                true
            }

            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }

            R.id.action_favorites -> {
                startActivity(Intent(this, FavoritesActivity::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun favoriteArticleButton() {
        binding.favArticlesBtn.setOnClickListener {
            val i = Intent(this, FavoritesActivity::class.java)
            startActivity(i)
        }
    }

    private fun generalButton() {
        binding.generalBtn.setOnClickListener {
            val i = Intent(this, NewsActivity::class.java)
            i.putExtra("category", "technology")
            startActivity(i)
        }
    }

    private fun sportBtn() {
        binding.sportBtn.setOnClickListener {
            val i = Intent(this, NewsActivity::class.java)
            i.putExtra("category", "sports")
            startActivity(i)

        }
    }


    private fun scienceBtn() {
        binding.scienceBtn.setOnClickListener {
            val i = Intent(this, NewsActivity::class.java)
            i.putExtra("category", "science")
            startActivity(i)

        }
    }

    private fun healthBtn() {
        binding.healthBtn.setOnClickListener {
            val i = Intent(this, NewsActivity::class.java)
            i.putExtra("category", "health")
            startActivity(i)
        }
    }

    private fun technologyBtb() {
        binding.technologyBtn.setOnClickListener {
            val i = Intent(this, NewsActivity::class.java)
            i.putExtra("category", "entertainment")
            startActivity(i)
        }
    }

    private fun businessBtn() {
        binding.businessBtn.setOnClickListener {
            val i = Intent(this, NewsActivity::class.java)
            i.putExtra("category", "business")
            startActivity(i)
        }
    }

    private fun loadNews() {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        sharedPreferences = getSharedPreferences("NewsPrefs", MODE_PRIVATE)
        val country = sharedPreferences.getString("selected_country", "us") ?: "us"

        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val c = retrofit.create(NewsCallable::class.java)
        c.getGeneralNews(
            category = null,
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
                }
            }


            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("trace", "Error :${t.message}")
                val articles = ArrayList<Article>()
                articles.add(Article(title = "Title"))
                articles.add(Article(title = "Title"))
                showNews(articles)
            }

        })
    }

    fun showNews(articles: ArrayList<Article>) {
        val adapter = HomeAdapter(this, articles)
        binding.newsList.adapter = adapter
    }


}
