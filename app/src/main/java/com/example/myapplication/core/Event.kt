package com.example.myapplication.core

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun setContentHasBeenHandled() {
        hasBeenHandled = true
    }

    fun getContent(): T {
        return content
    }

    /**
     * This method simplifies the use of getContentIfNotHandled() function
     * event.getContentIfNotHandled()?.let { }
     * can be replaced with
     * event.handleEvent { }
     */
    inline fun <R> handleEvent(block: (T) -> R): R? {
        return getContentIfNotHandled()?.let {
            block(it)
        }
    }
}