package com.example.hw6architecture.utils

import android.app.Application
import android.widget.Toast

class ToastMaker() {

    private lateinit var application: Application

    fun showToast(text: String) {
        Toast.makeText(application, text, Toast.LENGTH_LONG).show()
    }

    companion object {
        val instance = ToastMaker()
        fun setup(application: Application) {
            instance.application = application
        }
    }
}