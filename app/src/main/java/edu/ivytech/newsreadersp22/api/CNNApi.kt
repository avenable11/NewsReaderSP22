package edu.ivytech.newsreadersp22.api

import retrofit2.Call
import retrofit2.http.GET

interface CNNApi {

    @GET("cnn_showbiz.rss")
    fun downloadArticles() : Call<CNNResponse>
}