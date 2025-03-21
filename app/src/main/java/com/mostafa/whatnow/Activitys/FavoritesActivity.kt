package com.mostafa.whatnow.Activitys

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.mostafa.whatnow.NewsApi.FavArticle
import com.mostafa.whatnow.databinding.ActivityFavoritesBinding

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var _db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadData()
        binding.toolbarBackBtn.setOnClickListener {
            val i = Intent(this, HomeScreenActivity::class.java)
            startActivity(i)
            finishAffinity()
        }
    }

    private fun loadData() {
        var articles: MutableList<FavArticle>
        _db = FirebaseFirestore.getInstance()
        _db.collection("news")
            .get()
            .addOnSuccessListener {
                articles = it.toObjects(FavArticle::class.java).toMutableList()
                showDate(articles)
            }
            .addOnFailureListener {
                Toast.makeText(this, "can't get articles right now", Toast.LENGTH_LONG).show()
            }

    }

    private fun showDate(articles: MutableList<FavArticle>) {
        binding.favItemsRecyclerView.adapter = FavoriteAdapter(this, articles)
    }
}