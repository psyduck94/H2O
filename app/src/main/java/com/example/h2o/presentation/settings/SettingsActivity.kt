package com.example.h2o.presentation.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.h2o.R
import com.example.h2o.domain.enums.InputState
import com.example.h2o.toast
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
        onGoalOfTheDayClick()
        initSettings()

        initViewModelObservers()
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun onGoalOfTheDayClick() {
        goal_of_the_day.setOnClickListener { setupGoalOfTheDayDialog() }
    }

    private fun setupGoalOfTheDayDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(
            R.layout.goal_of_the_day_dialog_layout,
            findViewById(R.id.goal_of_the_day_dialog_container)
        )
        val goalOfTheDayInput = dialogView.findViewById<EditText>(R.id.goal_of_the_day_input)
        dialogBuilder.setView(dialogView)

        val dialog = dialogBuilder.show()
        dialogView.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            viewModel.saveGoalOfTheDayToCache(goalOfTheDayInput.text.toString())
            viewModel.updateGoalOfTheDayFromLocalDatabase(goalOfTheDayInput.text.toString().toInt()/100)
            dialog.dismiss()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initViewModelObservers() {
        viewModel.apply {
            goalOfTheDayInputState.observe(this@SettingsActivity, Observer { state ->
                when (state) {
                    InputState.VALID -> {
                        goal_of_the_day.text = "${viewModel.fetchGoalOfTheDayFromCache()}ml"
                    }
                    InputState.EMPTY -> toast(getString(R.string.warning_empty_number))
                    InputState.INVALID -> toast(getString(R.string.warning_invalid_goal))
                    else -> toast(getString(R.string.warning_generic_error))
                }
            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}