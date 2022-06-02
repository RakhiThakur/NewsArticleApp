package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.api.ApiServices
import com.example.myapplication.api.NoConnectionInterceptor
import com.example.myapplication.feature.news.repo.NewsRepo
import com.example.myapplication.feature.news.repo.NewsRepoImpl
import com.example.myapplication.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl(): String {
        return Constants.BASE_URL
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(noConnectionInterceptor: NoConnectionInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            readTimeout(200, TimeUnit.SECONDS)
            connectTimeout(200, TimeUnit.SECONDS)
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            addInterceptor(loggingInterceptor)
            addInterceptor(noConnectionInterceptor)
        }.build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }

    @Provides
    @Singleton
    fun provideNoConnectionInterceptor(@ApplicationContext appContext: Context): NoConnectionInterceptor {
        return NoConnectionInterceptor(appContext)
    }

    @Provides
    @Singleton
    fun provideNewsRepo(apiServices: ApiServices): NewsRepo {
        return NewsRepoImpl(apiServices = apiServices)
    }
}