package com.example.myapplication.feature.news.repo

import com.example.myapplication.api.NetworkResult
import com.example.myapplication.feature.news.data.NewsListResponse
import kotlinx.coroutines.flow.Flow

interface NewsRepo {
    suspend fun fetchNewsList(): Flow<NetworkResult<NewsListResponse>>
}