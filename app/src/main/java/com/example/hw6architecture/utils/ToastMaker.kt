package com.example.hw6architecture.utils

import android.content.Context
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToastMaker @Inject constructor(
    @ApplicationContext private val application: Context
) {
    fun showToast(text: String) {
        Toast.makeText(application, text, Toast.LENGTH_LONG).show()
    }
}