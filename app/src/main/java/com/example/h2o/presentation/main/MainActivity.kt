package com.example.h2o.presentation.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.h2o.R
import com.example.h2o.presentation.settings.SettingsActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sdsmdg.harjot.crollerTest.Croller
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val viewModel by inject<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()
        onAddWaterButtonClick()
        initViewModelObservers()
    }

    override fun onResume() {
        super.onResume()
        Log.d("onResume", "was called")
        initSettings()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    @SuppressLint("SetTextI18n")
    private fun initSettings() {
        water_progress.text = "${viewModel.fetchCurrentWaterValueFromCache()}%"
        waveLoadingView.progressValue = viewModel.fetchCurrentWaterValueFromCache()
        updateActionBarTitle()
    }

    @SuppressLint("SetTextI18n")
    private fun initViewModelObservers() {
        viewModel.apply {
            waterProgress.observe(this@MainActivity, Observer {
                water_progress.text = "$it%"
                waveLoadingView.progressValue = it
            })
        }
    }

    private fun onAddWaterButtonClick() {
        btn_add_water.setOnClickListener { showBottomSheetDialog() }
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val bottomSheetView =
            layoutInflater.inflate(R.layout.bottom_sheet, findViewById(R.id.bottomSheetContainer))
        val seekBar = bottomSheetView.findViewById<Croller>(R.id.seekBar)
        val waterQuantityTextView = bottomSheetView.findViewById<TextView>(R.id.water_quantity)

        bottomSheetView.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            viewModel.updateWaterProgress(waterQuantityTextView.text, seekBar.max)
            updateActionBarTitle()
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.setContentView(bottomSheetView)
        setupSeekBar(seekBar, waterQuantityTextView)
        bottomSheetDialog.show()
    }

    private fun updateActionBarTitle() {
        supportActionBar?.title = viewModel.getCurrentWaterInMl()
    }

    @SuppressLint("SetTextI18n")
    private fun setupSeekBar(seekBar: Croller, waterQuantityTextView: TextView) {
        seekBar.max = viewModel.fetchGoalOfTheDayFromCache()
        seekBar.setOnProgressChangedListener { waterQuantityTextView.text = "${it*100}ml" }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> startActivity(SettingsActivity.getIntent(this))
        }
        return super.onOptionsItemSelected(item)
    }
}