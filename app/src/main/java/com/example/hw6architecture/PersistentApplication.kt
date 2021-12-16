package com.example.hw6architecture

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.hw6architecture.data.local.MoviesRoom
import com.example.hw6architecture.utils.Utils

class PersistentApplication : Application() {

    lateinit var viewModel: MoviesViewModel

    override fun onCreate() {
        super.onCreate()
        Utils.setup(this)
        viewModel =
            ViewModelProvider.AndroidViewModelFactory(this).create(MoviesViewModel::class.java)

    }
}