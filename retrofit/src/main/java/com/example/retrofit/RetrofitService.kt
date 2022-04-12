package com.example.retrofit

import com.example.retrofit.api.ApiService
import com.example.retrofit.api.ModelArticle
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException

object RetrofitService {
    private val retrofitService: ApiService by lazy { makeRetrofit() }

    private fun makeRetrofit(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    suspend fun getModelArticles(searchString: String): List<ModelArticle> {
        val resp = retrofitService.search(searchString)
        return resp.body()
            ?: throw ConnectException(resp.errorBody()?.toString() ?: "No response from server")
    }
}