package edu.ivytech.newsreadersp22

import android.app.Application

class NewsReaderApplication : Application() {
        override fun onCreate() {
            super.onCreate()
            ArticleRepository.initialize(this)
            ArticleRepository.get().fetchArticles()
        }
}