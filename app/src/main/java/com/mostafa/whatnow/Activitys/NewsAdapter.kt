package com.mostafa.whatnow.Activitys


import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.mostafa.whatnow.NewsApi.Article
import com.mostafa.whatnow.R
import com.mostafa.whatnow.databinding.ArticlesListItemBinding

class NewsAdapter(private val a: Activity, private val articles: ArrayList<Article>) :
    Adapter<NewsAdapter.NewsViewHolder>() {
    class NewsViewHolder(val binding: ArticlesListItemBinding) : ViewHolder(binding.root)

    private val _db: FirebaseFirestore = FirebaseFirestore.getInstance()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val b = ArticlesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(b)
    }

    override fun getItemCount() = articles.size


    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.articleText.text = articles[position].title
        Glide
            .with(holder.binding.articleImage.context)
            .load(articles[position].imageUrl)
            .error(R.drawable.broken_image)
            .transition(DrawableTransitionOptions.withCrossFade(1000))
            .into(holder.binding.articleImage)

        val url = articles[position].url

        holder.binding.articlesContainer.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, url.toUri())
            a.startActivity(i)
        }

        holder.binding.shareFab.setOnClickListener {
            ShareCompat
                .IntentBuilder(a)
                .setType("text/plain")
                .setChooserTitle("Share Articles With : ")
                .setText(url)
                .startChooser()
        }

        holder.binding.addToFavBtn.setOnClickListener {

            val db =  _db.collection("news").document(articles[position].title)
            val article = hashMapOf(
                "title" to articles[position].title,
                "url" to articles[position].url
            )

            db.set(article, SetOptions.merge())
            .addOnSuccessListener {
                Toast.makeText(a, "Add to favorite", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(a, "No internet. Data will sync when online", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }
}