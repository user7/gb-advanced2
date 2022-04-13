package com.example.retrofit.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("words/search")
    suspend fun search(@Query("search") searchQuery: String): Response<List<ModelArticle>>

    companion object {
        const val BASE_URL = "https://dictionary.skyeng.ru/api/public/v1/"
    }
}

data class ModelArticle(
    val text: String?,
    val meanings: List<ModelMeaning>?,
)

data class ModelMeaning(
    val translation: ModelTranslation?,
    val previewUrl: String?,
    val imageUrl: String?,
)

data class ModelTranslation(
    val text: String?,
    val note: String?,
)

/* ПРИМЕР ЗАПРОСА *

https://dictionary.skyeng.ru/api/public/v1/words/search?search=cat

[
  {
    "id": 1560,
    "text": "cat",
    "meanings": [
      {
        "id": 65977,
        "partOfSpeechCode": "n",
        "translation": {
          "text": "кот",
          "note": "кошка"
        },
        "previewUrl": "//cdn-user77752.skyeng.ru/resized-images/96x72/jpeg/60/55bd5010ef32706be7b7e371673c1b1c.jpeg",
        "imageUrl": "//cdn-user77752.skyeng.ru/resized-images/640x480/jpeg/60/55bd5010ef32706be7b7e371673c1b1c.jpeg",
        "transcription": "kæt",
        "soundUrl": "https://vimbox-tts.skyeng.ru/api/v1/tts?text=cat&lang=en&voice=male_2"
      },
      {
        "id": 65983,
        "partOfSpeechCode": "n",
        "translation": {
          "text": "животное семейства кошачьих",
          "note": ""
        },
        "previewUrl": "//cdn-user77752.skyeng.ru/resized-images/96x72/jpeg/60/97f82053327fe84e11bc176879483d90.jpeg",
        "imageUrl": "//cdn-user77752.skyeng.ru/resized-images/640x480/jpeg/60/97f82053327fe84e11bc176879483d90.jpeg",
        "transcription": "kæt",
        "soundUrl": "https://vimbox-tts.skyeng.ru/api/v1/tts?text=cat&lang=en&voice=male_2"
      },
      ...
*/