package com.mostafa.whatnow.Activitys

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.firestore.FirebaseFirestore
import com.mostafa.whatnow.NewsApi.FavArticle
import com.mostafa.whatnow.databinding.ItemFavoriteArticleBinding

class FavoriteAdapter(private val a: Activity, private val articles: MutableList<FavArticle>) :
    Adapter<FavoriteAdapter.ArticlesHolder>() {
    class ArticlesHolder(val binding: ItemFavoriteArticleBinding) : ViewHolder(binding.root)

    private val _db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesHolder {
        val b =
            ItemFavoriteArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticlesHolder(b)
    }

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: ArticlesHolder, position: Int) {
        holder.binding.headLineTv.text = articles[position].title

        holder.binding.linkButton.setOnClickListener {
            val url = articles[position].url
            val i = Intent(Intent.ACTION_VIEW, url.toUri())
            a.startActivity(i)
        }
        holder.binding.deleteFab.setOnClickListener {
            val title = articles[position].title
            val index = articles.indexOfFirst { it.title == articles[position].title }
            if (index != -1) {
                articles.removeAt(index)
                notifyItemRemoved(index)
                notifyItemRangeChanged(index, articles.size)
            }

            _db.collection("news")
                .document(title)
                .delete()
                .addOnFailureListener {
                    Toast.makeText(a, "Article Removed", Toast.LENGTH_SHORT).show()
                }
        }

    }


}