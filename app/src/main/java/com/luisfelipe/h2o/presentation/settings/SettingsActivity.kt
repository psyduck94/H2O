package com.luisfelipe.h2o.presentation.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.luisfelipe.h2o.R
import com.luisfelipe.h2o.databinding.ActivitySettingsBinding
import com.luisfelipe.h2o.domain.enums.InputState
import com.luisfelipe.h2o.toast
import org.koin.android.ext.android.inject

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val viewModel by inject<SettingsViewModel>()

    companion object {
        fun getIntent(context: Context) = Intent(context, SettingsActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBindingConfig()

        initToolbarTitle()
        setAppVersionCode()
        initViewModelObservers()

        binding.goalOfTheDay.setOnClickListener { setupGoalOfTheDayDialog() }
        binding.timeReminder.setOnClickListener { openTimePicker() }
    }

    private fun initBindingConfig() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initToolbarTitle() {
        this.title = "Settings"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    @SuppressLint("SetTextI18n")
    private fun setAppVersionCode() {
        val manager: PackageManager = applicationContext.packageManager
        val info: PackageInfo = manager.getPackageInfo(applicationContext.packageName, 0)
        val versionName = info.versionName
        binding.appVersion.text = "v${versionName}"
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
            dialog.dismiss()
        }
    }

    private fun openTimePicker() {
        val dialogBuilder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(
            R.layout.number_picker_dialog_layout,
            findViewById(R.id.number_picker_container)
        )

        dialogBuilder.setView(dialogView)

        val numberPicker = setUpNumberPicker(dialogView)
        val dialog = dialogBuilder.show()

        dialogView.findViewById<TextView>(R.id.btn_positive).setOnClickListener {
            getSelectedValueFromTimePicker(numberPicker, dialog)
        }
    }

    private fun getSelectedValueFromTimePicker(
        numberPicker: NumberPicker?,
        dialog: AlertDialog
    ) {
        val selectedValue = numberPicker?.value
        toast(selectedValue.toString())
        dialog.dismiss()
    }

    private fun setUpNumberPicker(dialogView: View): NumberPicker? {
        val numberPicker = dialogView.findViewById<NumberPicker>(R.id.number_picker)
        numberPicker.maxValue = 2
        numberPicker.minValue = 0
        numberPicker.displayedValues = arrayOf("Every 1 hour", "Every 2 hours", "Every 3 hours")
        return numberPicker
    }

    @SuppressLint("SetTextI18n")
    private fun initViewModelObservers() {
        viewModel.apply {
            goalOfTheDayInputState.observe(this@SettingsActivity, Observer { state ->
                when (state) {
                    InputState.VALID -> {
                        binding.goalOfTheDay.text = "${viewModel.fetchGoalOfTheDayFromCache()}ml"
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