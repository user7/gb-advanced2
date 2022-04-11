package com.gb.advanced2.entities

data class Article(
    val term: String,
    val meanings: List<Meaning>,
)

typealias Articles = List<Article>