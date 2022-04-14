package edu.ivytech.newsreadersp22.api


import com.google.gson.annotations.SerializedName

data class NYTResponse(var results : List<NYTArticle>)

data class NYTArticle(val title : String, //@SerializedName("headline") val title: String,
                      @SerializedName("published_date") val date: String,//@SerializedName("publication_date") val date: String,
                      @SerializedName("abstract") val description: String,//@SerializedName("summary_short") val description: String,
                      @SerializedName("url") val link : String//val link: Link
                      )
data class Link(val url : String?)
