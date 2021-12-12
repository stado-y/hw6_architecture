package com.example.hw6architecture

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hw6architecture.data.ImdbRepository

class PersistentApplication : Application() {


    val viewModel = ViewModelProvider.AndroidViewModelFactory(this).create(MoviesViewModel::class.java)


    init {


    }

    companion object {

        fun makeToast(message: String, length: Int) {

            Toast.makeText((this as Application).applicationContext, message, length).show()
        }
    }
}