package com.gb.advanced2.externals.repo

import android.util.Log
import com.gb.advanced2.app.Contract
import com.gb.advanced2.entities.Article
import com.gb.advanced2.entities.Articles
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class RemoteRepository : Contract.Model {
    private val service: ApiService = makeRetrofit()

    private fun makeRetrofit(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dictionary.skyeng.ru/api/public/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    interface ApiService {
        @GET("words/search")
        fun search(@Query("search") searchQuery: String): Observable<List<ModelResponseEntry>>
    }

    override fun getArticles(searchString: String): Observable<Articles> =
        // конвертация retrofit объектов в entities.Article
        service.search(searchString).map { entries ->
            val out = ArrayList<Article>()
            for (entry in entries) {
                if (entry.text == null || entry.meanings == null)
                    continue
                val sb = StringBuilder()
                for (meaning in entry.meanings) {
                    val trans = meaning.translation ?: continue

                    // разделение нескольких смыслов через ;
                    if (sb.isNotEmpty()) {
                        sb.append("; ")
                    }

                    // основной текст
                    trans.text?.let { sb.append(it) }

                    // пояснительный текст
                    if (trans.note != null && trans.note != "") {
                        sb.append(" (${trans.note})")
                    }
                }
                out.add(Article(entry.text, sb.toString()))
            }
            out
        }

    data class ModelResponseEntry(
        val text: String?,
        val meanings: List<ModelMeaning>?,
    )

    data class ModelMeaning(
        val translation: ModelTranslation?,
    )

    data class ModelTranslation(
        val text: String?,
        val note: String?,
    )
}