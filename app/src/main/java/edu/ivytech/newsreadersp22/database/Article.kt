package edu.ivytech.newsreadersp22.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Article(var title:String = "",
                   var pubDate: Date? = Date(),
                   var url:String? = "",
                   var description:String = "",
                   @PrimaryKey val uuid: UUID = UUID.randomUUID())