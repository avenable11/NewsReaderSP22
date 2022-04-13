package edu.ivytech.newsreadersp22.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.util.*

@Dao
interface ArticleDao {
    @Query("Select * from Article")
    fun getArticles() : LiveData<List<Article>>

    @Query("Select * from Article where uuid = :id")
    fun getArticle(id : UUID) : LiveData<Article?>

    @Query("Delete from Article")
    fun deleteArticles()

    @Insert
    fun insertArticle(article : Article)
}