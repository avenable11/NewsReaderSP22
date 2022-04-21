package edu.ivytech.newsreadersp22

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            this.findPreference<ListPreference>("source")?.setOnPreferenceChangeListener {
                preference, newValue ->
                val pref = PreferenceManager.getDefaultSharedPreferences(requireContext())
                pref.edit().putString(preference.key, newValue as String).apply()
                val workRequest = OneTimeWorkRequest.Builder(DownloadWorker::class.java).build()
                WorkManager.getInstance(requireContext()).enqueue(workRequest)
                true
            }
        }
    }
}