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

    @Query("Delete from ArticleHistory")
    fun deleteHistory()

    @Query("insert into articlehistory select * from article")
    fun copyHistory()

    @Query("Select count(*) from Article where title not in (select title from ArticleHistory)")
    fun getNewArticles() : Int

    @Insert
    fun insertArticle(article : Article)
}