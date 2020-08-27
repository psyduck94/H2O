package com.example.h2o.presentation.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.h2o.R
import kotlinx.android.synthetic.main.activity_settings.*
import org.koin.android.ext.android.inject

class SettingsActivity : AppCompatActivity() {

    private val viewModel by inject<SettingsViewModel>()

    companion object {
        fun getIntent(context: Context) = Intent(context, SettingsActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        initToolbarTitle()
        setAppVersionCode()
        initSettings()
    }

    @SuppressLint("SetTextI18n")
    private fun setAppVersionCode() {
        val manager: PackageManager = applicationContext.packageManager
        val info: PackageInfo = manager.getPackageInfo(applicationContext.packageName, 0)
        val versionName = info.versionName
        app_version.text = "v${versionName}"
    }

    @SuppressLint("SetTextI18n")
    private fun initSettings() {
        goal_of_the_day.text = "${viewModel.fetchGoalOfTheDayFromCache()}ml"
    }

    private fun initToolbarTitle() {
        this.title = "Settings"
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}