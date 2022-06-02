package com.example.myapplication.feature.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.myapplication.databinding.ItemNewsBinding
import com.example.myapplication.feature.news.adapter.viewholder.NewsViewHolder
import com.example.myapplication.feature.news.model.ArticleUIModel
import com.example.myapplication.feature.sharedviewmodel.NewsSharedViewModel
import javax.inject.Inject

class NewsAdapter @Inject constructor(private val viewModel: NewsSharedViewModel) :
    ListAdapter<ArticleUIModel, NewsViewHolder>(NewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsBinding.inflate(inflater, parent, false)
        return NewsViewHolder(viewModel, binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindData(getItem(position), position)
    }
}