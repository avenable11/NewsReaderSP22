package edu.ivytech.newsreadersp22

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import edu.ivytech.newsreadersp22.api.NYTApi
import edu.ivytech.newsreadersp22.api.NYTArticle
import edu.ivytech.newsreadersp22.api.NYTResponse
import edu.ivytech.newsreadersp22.database.Article
import edu.ivytech.newsreadersp22.database.ArticleDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

private const val DATABASE_NAME = "NewsReader.db"
private const val TAG = "Article Repo"
class ArticleRepository private constructor(private val context : Context) {
    private val database : ArticleDatabase = Room.databaseBuilder(context,
        ArticleDatabase::class.java,
        DATABASE_NAME).build()
    private val articleDao = database.articleDao()
    private val executor : Executor = Executors.newSingleThreadExecutor()
    private val nytApi : NYTApi
    init {
        val retrofitNYT : Retrofit = Retrofit.Builder()
            //.baseUrl("https://api.nytimes.com/svc/movies/v2/reviews/")
            .baseUrl("https://api.nytimes.com/svc/topstories/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        nytApi = retrofitNYT.create(NYTApi::class.java)
    }
    fun fetchArticles() {
        val responseData : MutableList<Article> = mutableListOf()
        val nytRequest : Call<NYTResponse> = nytApi.downloadArticles()
        nytRequest.enqueue(object : Callback<NYTResponse>{
            override fun onResponse(call: Call<NYTResponse>, response: Response<NYTResponse>) {
                Log.d(TAG, "Response Received")
                val nytResponse : NYTResponse? = response.body()
                val articles : List<NYTArticle>? = nytResponse?.results
                if(articles != null)
                {
                    for(a in articles)
                    {
                        //val date = SimpleDateFormat("yyyy-mm-dd", Locale.US).parse(a.date)
                        val date = SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ssZ", Locale.US).parse(a.date)
                        //val article = Article(a.title, date, a.link.url, a.description)
                        val article = Article(a.title, date, a.link, a.description)
                        responseData += article
                    }
                    insertArticles(responseData)
                } else {
                    Log.e(TAG, "Response is coming back null")
                }
            }

            override fun onFailure(call: Call<NYTResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch articles", t)
            }

        })
    }

    //https://api.nytimes.com/svc/movies/v2/reviews/picks.json?api-key=ER6zIML0n1M6Dkdquqy7yHPtUgfkOcn3
    //https://api.nytimes.com/svc/topstories/v2/movies.json?api-key=ER6zIML0n1M6Dkdquqy7yHPtUgfkOcn3


    private fun insertArticles(responseData: List<Article>) {
        executor.execute{articleDao.deleteArticles()
        for (a in responseData){
            insertArticle(a)
        }
        }

    }

    fun getArticles() : LiveData<List<Article>> = articleDao.getArticles()
    fun getArticle(id : UUID) : LiveData<Article?> = articleDao.getArticle(id)

    private fun insertArticle(article: Article)
    {
        executor.execute{
            articleDao.insertArticle(article)
        }
    }

    companion object {
        private var INSTANCE : ArticleRepository? = null

        fun initialize(context: Context) {
            INSTANCE = ArticleRepository(context)
        }

        fun get() : ArticleRepository {
            return INSTANCE?: throw IllegalStateException("Article Repository msut be initialized.")
        }
    }
}