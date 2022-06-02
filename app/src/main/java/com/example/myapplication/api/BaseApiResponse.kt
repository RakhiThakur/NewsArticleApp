package com.example.myapplication.api

import android.accounts.NetworkErrorException
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response

abstract class BaseApiResponse {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            var errorJsonArray: JSONArray? = null
            var errorMessage: String? = null
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            }
            try {
                val jObjError = JSONObject(response.errorBody()?.string().orEmpty())
                errorJsonArray = jObjError.getJSONArray("message")
                errorMessage = errorJsonArray?.get(0) as String
            } catch (e: Exception) {
                errorMessage = e.message
            }
            return error(errorMessage.orEmpty())
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        } catch (e: NetworkErrorException) {
            return error(e.message ?: e.toString())
        } catch (e: InternetNotAvailableException) {
            return error("No Internet Connection")
        }
    }

    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error(errorMessage)
}