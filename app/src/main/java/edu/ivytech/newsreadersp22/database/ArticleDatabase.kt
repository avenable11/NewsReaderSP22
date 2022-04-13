package edu.ivytech.newsreadersp22.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters




@Database(entities=[Article::class], version = 1)
@TypeConverters(ArticleTypeConverters::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao() : ArticleDao
}