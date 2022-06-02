package com.example.myapplication.feature.news.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.feature.news.model.ArticleUIModel

class NewsDiffCallback : DiffUtil.ItemCallback<ArticleUIModel>() {
    override fun areItemsTheSame(oldItem: ArticleUIModel, newItem: ArticleUIModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ArticleUIModel, newItem: ArticleUIModel): Boolean {
        return oldItem == newItem
    }
}