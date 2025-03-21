package com.mostafa.whatnow.Activitys

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mostafa.whatnow.NewsApi.Article
import com.mostafa.whatnow.R
import com.mostafa.whatnow.databinding.TopHeaderRecyleviewItemBinding

class HomeAdapter(private val a: Activity, private val articles: ArrayList<Article>) :
    RecyclerView.Adapter<HomeAdapter.NewsViewHolder>() {
    class NewsViewHolder(val binding: TopHeaderRecyleviewItemBinding) : ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val b = TopHeaderRecyleviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NewsViewHolder(b)
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        holder.binding.headLineTv.text = articles[position].title
        holder.binding.topHeaderCard.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, articles[position].url.toUri())
            a.startActivity(i)
        }
        Glide
            .with(holder.binding.newImage.context)
            .load(articles[position].imageUrl)
            .error(R.drawable.broken_image)
            .transition(DrawableTransitionOptions.withCrossFade(200))
            .into(holder.binding.newImage)
    }


}