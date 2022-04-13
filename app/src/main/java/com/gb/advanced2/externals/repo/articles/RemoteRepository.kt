package com.gb.advanced2.externals.repo.articles

import com.example.retrofit.RetrofitService
import com.gb.advanced2.app.Contract
import com.gb.advanced2.entities.Meaning
import com.gb.advanced2.entities.Article
import com.gb.advanced2.entities.Articles
import java.util.*

class RemoteRepository : Contract.ArticlesModel {
    override suspend fun getArticles(searchString: String): Articles {
        val body = RetrofitService.getModelArticles(searchString)
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