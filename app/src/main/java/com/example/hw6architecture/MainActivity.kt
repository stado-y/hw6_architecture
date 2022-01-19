package com.example.hw6architecture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.hw6architecture.databinding.ActivityMainBinding
import com.example.hw6architecture.moviedetails.MovieDetailsFragment
import com.example.hw6architecture.movielist.ItemClickListener
import com.example.hw6architecture.movielist.Movie
import com.example.hw6architecture.movielist.MovieListFragment
import com.example.hw6architecture.movielist.MovieListFragmentDirections
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}