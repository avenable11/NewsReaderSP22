package edu.ivytech.newsreadersp22.ui.articlelist

import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.ivytech.newsreadersp22.database.Article
import edu.ivytech.newsreadersp22.databinding.FragmentArticleListBinding
import edu.ivytech.newsreadersp22.databinding.ListItemBinding

class ArticleListFragment : Fragment() {

private lateinit var binding : FragmentArticleListBinding
private val viewModel: ArticleListViewModel by viewModels()
private var adapter : ArticleAdapter = ArticleAdapter(emptyList())


override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
): View {
    binding = FragmentArticleListBinding.inflate(inflater, container, false)
    binding.articleList.layoutManager = LinearLayoutManager(context)
    binding.articleList.adapter = adapter
    return binding.root
}

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel.articleListLiveData.observe(viewLifecycleOwner) { articles ->
        adapter = ArticleAdapter(articles)
        binding.articleList.adapter = adapter
    }
}

private inner class ArticleViewHolder(val itemBinding: ListItemBinding)
    : RecyclerView.ViewHolder(itemBinding.root) {
    private lateinit var article : Article
    fun bind(article:Article) {
        this.article = article
        itemBinding.listArticleTitle.text = article.title
        itemBinding.listPubDate.text = DateFormat.format("EEEE, MMM, dd, yyyy", article.pubDate).toString()
    }
}

private inner class ArticleAdapter(val articles : List<Article>) : RecyclerView.Adapter<ArticleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val itemBinding : ListItemBinding = ListItemBinding.inflate(layoutInflater,parent, false)
        return ArticleViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount(): Int  = articles.size

}

}