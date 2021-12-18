package com.example.hw6architecture

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.hw6architecture.data.ImdbRepository
import com.example.hw6architecture.data.local.MoviesRoom
import com.example.hw6architecture.utils.ToastMaker

class Hw6Architecture : Application() {


    lateinit var viewModel: MoviesViewModel

    override fun onCreate() {
        super.onCreate()

        ImdbRepository.create(this)

        viewModel =
            ViewModelProvider.AndroidViewModelFactory(this).create(MoviesViewModel::class.java)

        ToastMaker.setup(this)
    }
}