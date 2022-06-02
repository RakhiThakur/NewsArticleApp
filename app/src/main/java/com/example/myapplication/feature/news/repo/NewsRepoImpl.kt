package com.example.myapplication.feature.news.repo

import com.example.myapplication.api.ApiServices
import com.example.myapplication.api.BaseApiResponse
import com.example.myapplication.api.NetworkResult
import com.example.myapplication.feature.news.data.NewsListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NewsRepoImpl @Inject constructor(private val apiServices: ApiServices) : NewsRepo, BaseApiResponse() {
    override suspend fun fetchNewsList(): Flow<NetworkResult<NewsListResponse>> {
        return flow {
            emit(safeApiCall { apiServices.getTopNewsList() })
        }.flowOn(Dispatchers.IO)
    }
}