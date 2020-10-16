package com.luisfelipe.h2o.presentation.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.luisfelipe.h2o.R
import com.luisfelipe.h2o.databinding.ActivityMainBinding
import com.luisfelipe.h2o.presentation.archives.ArchivesActivity
import com.luisfelipe.h2o.presentation.settings.SettingsActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.luisfelipe.h2o.domain.enums.WaterAction
import com.sdsmdg.harjot.crollerTest.Croller
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by inject<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBindingConfig()
        setSupportActionBar(toolbar)
        btn_add_water.setOnClickListener { initBottomSheetDialog(WaterAction.ADD) }
        btn_remove_water.setOnClickListener { initBottomSheetDialog(WaterAction.REMOVE) }

    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchWaterLogFromLocalDb()
    }

    private fun initBindingConfig() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initBottomSheetDialog(action: WaterAction) {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val bottomSheetView =
            layoutInflater.inflate(R.layout.bottom_sheet, findViewById(R.id.bottomSheetContainer))
        val seekBar = bottomSheetView.findViewById<Croller>(R.id.seekBar)
        val waterQuantityTextView = bottomSheetView.findViewById<TextView>(R.id.water_quantity)

        bottomSheetView.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            viewModel.updateWater(waterQuantityTextView.text.toString(), action)
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.setContentView(bottomSheetView)
        setupSeekBar(seekBar, waterQuantityTextView)
        bottomSheetDialog.show()
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
            R.id.action_archives -> startActivity(ArchivesActivity.getIntent(this))
        }
        return super.onOptionsItemSelected(item)
    }
}