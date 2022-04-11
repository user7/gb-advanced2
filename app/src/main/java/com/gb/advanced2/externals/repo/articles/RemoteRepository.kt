package com.gb.advanced2.externals.repo.articles

import com.gb.advanced2.app.Contract
import com.gb.advanced2.entities.Meaning
import com.gb.advanced2.entities.Article
import com.gb.advanced2.entities.Articles
import com.gb.advanced2.externals.repo.articles.retrofit.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.util.*

class RemoteRepository : Contract.ArticlesModel {
    private val retrofitService: ApiService by lazy { makeRetrofit() }

    private fun makeRetrofit(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    override suspend fun getArticles(searchString: String): Articles {
        val resp = retrofitService.search(searchString)
        val body = resp.body()
            ?: throw ConnectException(resp.errorBody()?.toString() ?: "No response from server")
        val articles = LinkedList<Article>()
        for (entry in body) {
            val term = entry.text ?: continue
            val meanings = entry.meanings ?: continue
            val out = LinkedList<Meaning>()
            for (meaning in meanings) {
                val translation = meaning.translation ?: continue
                val text = translation.text ?: continue
                val note = translation.note ?: ""
                val desc = if (note.isEmpty()) text else "$text ($note)"
                out.add(Meaning(desc = desc, imageUrl = meaning.imageUrl?.let { "https:$it" }))
            }
            if (out.isNotEmpty())
                articles.add(Article(term = term, meanings = out))
        }
        return articles
    }
}