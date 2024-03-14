package com.app.githubApp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.githubApp.DetailActivity
import com.app.githubApp.databinding.ListUsersBinding
import com.app.githubApp.remote.response.UsersItem
import com.squareup.picasso.Picasso

class UsersAdapter : ListAdapter<UsersItem, UsersAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(private val bind: ListUsersBinding) : RecyclerView.ViewHolder(bind.root) {
        fun binding(user: UsersItem){
            bind.namaProfile.text = user.login
            Picasso.get().load(user.avatarUrl).into(bind.fotoProfile)

            itemView.setOnClickListener {
                val i = Intent(it.context, DetailActivity::class.java)
                i.putExtra(DetailActivity.NAMA, user.login)
                it.context.startActivity(i)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bind = ListUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.binding(user)
    }

    companion object {
       val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UsersItem>() {
           override fun areItemsTheSame(oldItem: UsersItem, newItem: UsersItem): Boolean {
               return oldItem == newItem
           }

           override fun areContentsTheSame(oldItem: UsersItem, newItem: UsersItem): Boolean {
               return oldItem == newItem
           }

       }
    }
}



