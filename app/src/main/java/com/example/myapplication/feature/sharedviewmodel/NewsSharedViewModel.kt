package com.example.myapplication.feature.sharedviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.example.myapplication.api.NetworkResult
import com.example.myapplication.core.Event
import com.example.myapplication.core.exhaustive
import com.example.myapplication.feature.news.model.ArticleUIModel
import com.example.myapplication.feature.news.model.NewsViewState
import com.example.myapplication.feature.news.repo.NewsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NewsSharedViewModel @Inject constructor(private val newsRepo: NewsRepo) : ViewModel() {

    private val _viewState = MutableLiveData<NewsViewState>()
    val viewState: LiveData<NewsViewState> get() = _viewState

    private val _navUiLiveData = MutableLiveData<Event<Navigation>>()
    val navigationLiveData: LiveData<Event<Navigation>>
        get() = _navUiLiveData

    init {
        if (_viewState.value == null) {
            _viewState.value = NewsViewState(isLoading = false)
        }
        fetchNewsList()
    }

    private fun fetchNewsList() {
        _viewState.value = _viewState.value?.copy(isLoading = true)
        viewModelScope.launch() {
            newsRepo.fetchNewsList().collect {
                _viewState.value = _viewState.value?.copy(isLoading = false)
                when (it) {
                    is NetworkResult.Error -> {
                        Timber.e("Server throws error")
                        _navUiLiveData.value = Event(Navigation.OnServerError(it.message.orEmpty()))
                    }
                    is NetworkResult.Loading -> {
                        Timber.e("Loading")
                    }
                    is NetworkResult.Success -> {
                        Timber.e("Success Response ${it.data}")
                        val articleList = arrayListOf<ArticleUIModel>()
                        it.data?.let { data ->
                            data.articles.forEach { article ->
                                articleList.add(
                                    ArticleUIModel(
                                        author = article.author.orEmpty(),
                                        content = article.content.orEmpty(),
                                        description = article.description.orEmpty(),
                                        publishedAt = convertISOTimeToDate(article.publishedAt.orEmpty()).toString(),
                                        title = article.title.orEmpty(),
                                        url = article.url.orEmpty(),
                                        urlToImage = article.urlToImage.orEmpty(),
                                        sourceName = article.source?.name.orEmpty()
                                    )
                                )
                            }
                        }
                        _viewState.value = _viewState.value?.copy(articleList = articleList)
                    }
                }.exhaustive
            }
        }
    }

    fun setSelectedItemPosition(position: Int) {
        _viewState.value = _viewState.value?.copy(selectedItemPosition = position)
    }

    fun onItemClicked(position: Int) {
        _navUiLiveData.value = Event(Navigation.OnItemClicked(position = position))
    }

    fun convertISOTimeToDate(isoTime: String): String? {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        var convertedDate: Date? = null
        var formattedDate: String? = null
        try {
            convertedDate = sdf.parse(isoTime)
            formattedDate = SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(convertedDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return formattedDate
    }

    sealed class Navigation {
        class OnServerError(val errorMessage: String) : Navigation()
        class OnItemClicked(val position: Int) : Navigation()
    }
}