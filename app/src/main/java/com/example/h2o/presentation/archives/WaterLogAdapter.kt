package com.example.h2o.presentation.archives

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.h2o.databinding.WaterLogItemBinding
import com.example.h2o.domain.models.WaterLog

class WaterLogAdapter : RecyclerView.Adapter<WaterLogAdapter.WaterLogViewHolder>() {

    private val waterLogList = mutableListOf<WaterLog>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaterLogViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val waterLogItemBinding = WaterLogItemBinding.inflate(layoutInflater, parent, false)
        return WaterLogViewHolder(waterLogItemBinding)
    }

    fun updateWaterLogList(waterLogList: List<WaterLog>) {
        if (this.waterLogList.isNotEmpty())
            this.waterLogList.clear()

        this.waterLogList.addAll(waterLogList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: WaterLogViewHolder, position: Int) =
        holder.bind(waterLogList[position])

    override fun getItemCount() = waterLogList.size

    class WaterLogViewHolder(private val waterLogBinding: WaterLogItemBinding) :
        RecyclerView.ViewHolder(waterLogBinding.root) {

        fun bind(waterLog: WaterLog) {
            waterLogBinding.waterLog = waterLog
        }
    }
}