package com.luisfelipe.h2o.presentation.archives

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.luisfelipe.h2o.R
import com.luisfelipe.h2o.databinding.ActivityArchivesBinding
import com.luisfelipe.h2o.defaultRecyclerViewLayout
import kotlinx.android.synthetic.main.activity_archives.*
import org.koin.android.ext.android.inject

class ArchivesActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context) = Intent(context, ArchivesActivity::class.java)
    }

    private lateinit var binding: ActivityArchivesBinding
    private val viewModel by inject<ArchivesViewModel>()
    private val waterLogAdapter by inject<WaterLogAdapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBindingConfig()
        initRecyclerView()
        initViewModelObservers()

        viewModel.fetchTheLast7WaterLogsFromLocalDb()
    }

    private fun initBindingConfig() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_archives)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initRecyclerView() {
        archives_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = defaultRecyclerViewLayout()
            adapter = waterLogAdapter
        }
    }

    private fun initViewModelObservers() {
        viewModel.apply {
            waterLogList.observe(this@ArchivesActivity, {
                waterLogAdapter.updateWaterLogList(it)
            })
        }
    }
}