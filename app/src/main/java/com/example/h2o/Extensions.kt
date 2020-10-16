package com.example.h2o

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

fun Activity.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Activity.defaultRecyclerViewLayout() = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

