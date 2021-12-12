package com.example.hw6architecture

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.hw6architecture.data.local.MoviesRoom

class PersistentApplication : Application() {


    lateinit var viewModel: MoviesViewModel

    lateinit var room: MoviesRoom


    override fun onCreate() {
        super.onCreate()

        viewModel = ViewModelProvider.AndroidViewModelFactory(this).create(MoviesViewModel::class.java)

    }

    companion object {

        fun makeToast(message: String, length: Int) {

            Toast.makeText((this as Application).applicationContext, message, length).show()
        }

    }
}