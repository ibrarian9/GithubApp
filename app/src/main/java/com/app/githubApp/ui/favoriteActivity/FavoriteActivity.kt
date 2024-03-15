package com.app.githubApp.ui.favoriteActivity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.githubApp.R
import com.app.githubApp.adapter.UsersAdapter
import com.app.githubApp.databinding.ActivityFavoriteBinding
import com.app.githubApp.remote.response.UsersItem
import com.app.githubApp.ui.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var bind: ActivityFavoriteBinding
    private val adapter = UsersAdapter()
    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(bind.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bind.rv.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
        }

        favoriteViewModel.getFavoriteUsers().observe(this){ data ->
            val dataList = arrayListOf<UsersItem>()
            data.map {
                val item = UsersItem(login = it.name, avatarUrl = it.avatarUrl!!)
                dataList.add(item)
            }
            adapter.submitList(dataList)

        }

    }
}