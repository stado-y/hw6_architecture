package com.example.hw6architecture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.hw6architecture.databinding.ActivityMainBinding
import com.example.hw6architecture.moviedetails.MovieDetailsFragment
import com.example.hw6architecture.movielist.ItemClickListener
import com.example.hw6architecture.movielist.Movie
import com.example.hw6architecture.movielist.MovieListFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ItemClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            createFragment(binding.MovieListFragmentContainer.id, MovieListFragment())
        }
    }

    override fun onItemClicked(movie: Movie) {
        createFragment(
            binding.MovieListFragmentContainer.id,
            MovieDetailsFragment.newInstance(movie.id),
            true
        )
    }

    private fun createFragment (
        idHolder: Int,
        fragment: Fragment,
        addToBackStack: Boolean = false
    ) {
        supportFragmentManager.beginTransaction().apply {
            replace(idHolder, fragment)
            if (addToBackStack) { addToBackStack(null) }
            commit()
        }
    }
}