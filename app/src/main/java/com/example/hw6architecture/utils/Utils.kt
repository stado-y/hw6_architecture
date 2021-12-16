package com.example.hw6architecture.utils

import android.app.Application
import android.widget.Toast

class Utils {
    private lateinit var application: Application

    fun showToast(text: String) {
        Toast.makeText(application, text, Toast.LENGTH_SHORT).show()
    }

    companion object {
        val instance = Utils()
        fun setup(application: Application) {
            instance.application = application
        }
    }

}