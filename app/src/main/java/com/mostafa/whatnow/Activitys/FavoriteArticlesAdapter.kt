package com.mostafa.whatnow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mostafa.whatnow.NewsApi.Article
import com.mostafa.whatnow.databinding.ItemFavoriteArticleBinding

class FavoriteArticlesAdapter(
    private val onLinkClick: (String) -> Unit
) : RecyclerView.Adapter<FavoriteArticlesAdapter.ArticleViewHolder>() {

    private var articles: List<Article> = emptyList()

    // Update the list of articles
    fun submitList(newArticles: List<Article>) {
        articles = newArticles
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemFavoriteArticleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount(): Int = articles.size

    inner class ArticleViewHolder(
        private val binding: ItemFavoriteArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.articleNameTextView.text = article.nam
            binding.articleLinkTextView.text = article.link

            // Set click listener for the link
            binding.articleLinkTextView.setOnClickListener {
                onLinkClick(article.link)
            }
        }
    }
}