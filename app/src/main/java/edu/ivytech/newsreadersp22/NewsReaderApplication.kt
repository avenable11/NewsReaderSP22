package edu.ivytech.newsreadersp22

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

const val DOWNLOAD_WORK = "download_work"
const val NOTIFICATION_CHANNEL_ID = "news_reader"
class NewsReaderApplication : Application() {
        override fun onCreate() {
            super.onCreate()
            ArticleRepository.initialize(this)
            //ArticleRepository.get().fetchArticles()
            var workRequest : PeriodicWorkRequest = PeriodicWorkRequest.Builder(
                DownloadWorker::class.java,
                1,
                TimeUnit.DAYS,
                1,
                TimeUnit.HOURS).build()
            WorkManager.getInstance(this).enqueueUniquePeriodicWork(DOWNLOAD_WORK,
                ExistingPeriodicWorkPolicy.KEEP, workRequest)
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            {
                val name = getString(R.string.notification_channel_name)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
                val notificationManager : NotificationManager = getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(channel)
            }

        }
}