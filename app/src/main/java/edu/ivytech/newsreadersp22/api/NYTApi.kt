package edu.ivytech.newsreadersp22.api

import retrofit2.Call
import retrofit2.http.GET

interface NYTApi {
    @GET("movies.json?api-key=ER6zIML0n1M6Dkdquqy7yHPtUgfkOcn3")
    fun downloadArticles() : Call<NYTResponse>
}