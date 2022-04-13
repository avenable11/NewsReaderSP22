package edu.ivytech.newsreadersp22

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import edu.ivytech.newsreadersp22.database.Article
import edu.ivytech.newsreadersp22.database.ArticleDatabase
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




    private fun insertArticles(responseData: List<Article>) {

        for (a in responseData){
            insertArticle(a)
        }

    }

    fun getArticles() : LiveData<List<Article>> = articleDao.getArticles()
    fun getArticle(id : UUID) : LiveData<Article?> = articleDao.getArticle(id)

    fun insertArticle(article: Article)
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