package com.luisfelipe.h2o.utils

import androidx.databinding.BindingAdapter
import me.itangqi.waveloadingview.WaveLoadingView

@BindingAdapter("waterProgressValue")
fun bindProgressValue(view: WaveLoadingView, progress: Int) {
    view.progressValue = progress
}