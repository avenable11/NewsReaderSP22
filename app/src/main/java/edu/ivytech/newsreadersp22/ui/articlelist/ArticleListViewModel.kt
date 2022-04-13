package edu.ivytech.newsreadersp22.ui.articlelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import edu.ivytech.newsreadersp22.ArticleRepository
import edu.ivytech.newsreadersp22.database.Article

class ArticleListViewModel : ViewModel() {
        private val articleRepository : ArticleRepository = ArticleRepository.get()
        val articleListLiveData : LiveData<List<Article>> = articleRepository.getArticles()
 }
