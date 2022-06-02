package com.example.myapplication.feature.news.adapter.viewholder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BR
import com.example.myapplication.feature.news.model.ArticleUIModel
import com.example.myapplication.feature.sharedviewmodel.NewsSharedViewModel
import javax.inject.Inject

class NewsViewHolder @Inject constructor(val viewModel: NewsSharedViewModel, val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindData(item: ArticleUIModel, position: Int) {
        with(binding) {
            setVariable(BR.viewModel, viewModel)
            setVariable(BR.articleUiModel, item)
            setVariable(BR.position, position)
            executePendingBindings()
        }
    }
}