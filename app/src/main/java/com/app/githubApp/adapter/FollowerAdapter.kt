package com.app.githubApp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.githubApp.databinding.ListUsersBinding
import com.app.githubApp.remote.response.FollResponseItem
import com.squareup.picasso.Picasso

class FollowerAdapter : ListAdapter<FollResponseItem, FollowerAdapter.ViewHolder>(DIFF_UTIL) {

    class ViewHolder(private val bind: ListUsersBinding) : RecyclerView.ViewHolder(bind.root) {
        fun binding(foll: FollResponseItem){
            bind.namaProfile.text = foll.login
            Picasso.get().load(foll.avatarUrl).into(bind.fotoProfile)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bind = ListUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foll = getItem(position)
        holder.binding(foll)
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<FollResponseItem>(){
            override fun areItemsTheSame(
                oldItem: FollResponseItem,
                newItem: FollResponseItem
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: FollResponseItem,
                newItem: FollResponseItem
            ): Boolean = oldItem == newItem

        }
    }
}