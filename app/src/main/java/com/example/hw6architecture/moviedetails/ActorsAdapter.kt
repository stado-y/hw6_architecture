package com.example.hw6architecture.moviedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hw6architecture.data.network.GlideModuleImplementation.Companion.fillImageViewFromURI
import com.example.hw6architecture.databinding.TopCastLayoutBinding
import com.example.hw6architecture.immutable_values.ImageSizes


class ActorsAdapter : ListAdapter<Actor, ActorsAdapter.ActorsViewHolder>(ActorDifUtil) {

    inner class ActorsViewHolder(
        private val binding: TopCastLayoutBinding
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsViewHolder {

        val view = TopCastLayoutBinding
            .inflate(LayoutInflater.from(parent.context),
                parent,
                false
                )
        return ActorsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActorsViewHolder, position: Int) {
        val current = getItem(position)
            holder.bind(current)
    }

    companion object {
        private val ActorDifUtil = object : DiffUtil.ItemCallback<Actor>() {
            override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
                return oldItem.name == newItem.name
                        && oldItem.character == newItem.character
            }
        }
    }
}