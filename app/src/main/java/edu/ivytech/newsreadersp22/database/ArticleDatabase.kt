package edu.ivytech.newsreadersp22.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities=[Article::class, ArticleHistory::class], version = 2)
@TypeConverters(ArticleTypeConverters::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao() : ArticleDao
}

val migrations_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("create table ArticleHistory (title text not null, " +
                "pubDate integer, url text, description text not null, uuid text not null primary key)")
    }
}