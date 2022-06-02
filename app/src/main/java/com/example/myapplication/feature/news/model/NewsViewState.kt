package com.example.myapplication.feature.news.model

data class NewsViewState(
    val isLoading: Boolean = false,
    val articleList: List<ArticleUIModel> = emptyList(),
    val selectedItemPosition: Int? = null
)