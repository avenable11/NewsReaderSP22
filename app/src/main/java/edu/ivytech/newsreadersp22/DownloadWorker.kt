package edu.ivytech.newsreadersp22

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

private const val TAG = "worker"
class DownloadWorker(val context: Context, workerParams : WorkerParameters)
    : Worker(context, workerParams) {
    override fun doWork(): Result {
        Log.d(TAG, "Worker triggered")
        ArticleRepository.get().fetchArticles()
        if(ArticleRepository.get().getNewArticles() > 0) {
            Log.d(TAG, "New Articles")
        } else {
            Log.d(TAG, "No new articles")
        }
        return Result.success()
    }

}