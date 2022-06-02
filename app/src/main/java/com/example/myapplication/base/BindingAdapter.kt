package com.example.myapplication.base

import android.text.method.LinkMovementMethod
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.feature.news.model.NewsViewState
import com.example.myapplication.utils.toHtml
import com.google.android.material.textview.MaterialTextView

@BindingAdapter("setNewsImage")
fun setNewsImage(imageView: ImageView, url: String) {
    Glide.with(imageView)
        .load(url)
        .placeholder(R.drawable.placeholder_image)
        .into(imageView)
}

@BindingAdapter("setNewsDetailsImage")
fun setNewsDetailsImage(imageView: ImageView, viewState: NewsViewState?) {
    if (viewState != null) {
        val selectedItemPosition = viewState.selectedItemPosition
        val imageUrl = viewState.articleList[selectedItemPosition ?: 0].urlToImage
        Glide.with(imageView)
            .load(imageUrl)
            .placeholder(R.drawable.placeholder_image)
            .into(imageView)
    }
}

@BindingAdapter("setHtmlText")
fun setHtmlText(textView: MaterialTextView, viewState: NewsViewState?) {
    if (viewState != null) {
        val selectedItemPosition = viewState.selectedItemPosition
        val text = viewState.articleList[selectedItemPosition ?: 0].content
        if (text.isNotEmpty()) {
            textView.movementMethod = LinkMovementMethod.getInstance()
            textView.text = "Content : ${text.toHtml()}"
        }
    }
}

@BindingAdapter("setSourceNameText")
fun setSourceNameText(textView: MaterialTextView, viewState: NewsViewState?) {
    if (viewState != null) {
        val selectedItemPosition = viewState.selectedItemPosition
        val text = viewState.articleList[selectedItemPosition ?: 0].sourceName
        if (text.isNotEmpty()) {
            textView.movementMethod = LinkMovementMethod.getInstance()
            textView.text = text
        }
    }
}

@BindingAdapter("setTitleText")
fun setTitleText(textView: MaterialTextView, viewState: NewsViewState?) {
    if (viewState != null) {
        val selectedItemPosition = viewState.selectedItemPosition
        val text = viewState.articleList[selectedItemPosition ?: 0].title
        if (text.isNotEmpty()) {
            textView.movementMethod = LinkMovementMethod.getInstance()
            textView.text = "Title : $text"
        }
    }
}

@BindingAdapter("setDescText")
fun setDescText(textView: MaterialTextView, viewState: NewsViewState?) {
    if (viewState != null) {
        val selectedItemPosition = viewState.selectedItemPosition
        val text = viewState.articleList[selectedItemPosition ?: 0].description
        if (text.isNotEmpty()) {
            textView.movementMethod = LinkMovementMethod.getInstance()
            textView.text = "Description : $text"
        }
    }
}

@BindingAdapter("setPublishedDateText")
fun setPublishedDateText(textView: MaterialTextView, viewState: NewsViewState?) {
    if (viewState != null) {
        val selectedItemPosition = viewState.selectedItemPosition
        val text = viewState.articleList[selectedItemPosition ?: 0].publishedAt
        if (text.isNotEmpty()) {
            textView.movementMethod = LinkMovementMethod.getInstance()
            textView.text = "$text"
        }
    }
}

