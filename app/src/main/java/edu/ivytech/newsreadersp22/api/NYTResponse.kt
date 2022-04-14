package edu.ivytech.newsreadersp22.api


import com.google.gson.annotations.SerializedName

data class NYTResponse(var results : List<NYTArticle>)

data class NYTArticle(@SerializedName("headline") val title: String,
                      @SerializedName("publication_date") val date: String,
                      @SerializedName("summary_short") val description: String,
                      val link: Link
                      )
data class Link(val url : String?)
