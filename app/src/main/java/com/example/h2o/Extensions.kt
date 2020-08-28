package com.example.h2o

import android.app.Activity
import android.widget.Toast

fun Activity.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}