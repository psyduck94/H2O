package com.luisfelipe.h2o

import android.app.Activity
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

fun Activity.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Activity.toast(stringResourceId: Int, duration: Int = Toast.LENGTH_SHORT) {
    val text = getString(stringResourceId)
    Toast.makeText(this, text, duration).show()
}

fun Activity.defaultRecyclerViewLayout() = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

