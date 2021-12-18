package com.example.hw6architecture.moviedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hw6architecture.data.network.GlideModuleImplementation.Companion.fillImageViewFromURI
import com.example.hw6architecture.databinding.TopCastLayoutBinding
import com.example.hw6architecture.immutable_values.ImageSizes

class ActorsAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var actorsList = emptyList<Actor>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    inner class ActorsViewHolder(
        val binding: TopCastLayoutBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Actor) {

            binding.actorNameTextView.text = item.name
            binding.actorRolesTextView.text = item.character

            fillImageViewFromURI(
                binding.actorPhotoImageView,
                item.photoURI,
                ImageSizes.Profile.MEDIUM.size
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = TopCastLayoutBinding
            .inflate(LayoutInflater.from(parent.context),
                parent,
                false
                )
        return ActorsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ActorsViewHolder) {

            holder.bind(actorsList[position])
        }
    }

    override fun getItemCount() = actorsList.count()



}