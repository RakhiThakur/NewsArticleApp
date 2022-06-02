package com.example.myapplication.api

import com.example.myapplication.feature.news.data.NewsListResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {

    @GET("/v2/everything?q=tesla&from=2022-05-01&sortBy=publishedAt&apiKey=7a8b39b4fc624c2fb3a99bd4772c1f3d")
    suspend fun getTopNewsList(): Response<NewsListResponse>
}