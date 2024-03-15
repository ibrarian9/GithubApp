package com.app.githubApp.ui.mainActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.githubApp.ui.darkModeActivity.DarkModeActivity
import com.app.githubApp.ui.favoriteActivity.FavoriteActivity
import com.app.githubApp.R
import com.app.githubApp.ui.ViewModelFactory
import com.app.githubApp.adapter.UsersAdapter
import com.app.githubApp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMainBinding
    private val adapter = UsersAdapter()
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.getThemeSet().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(bind) {
            rv.layoutManager = LinearLayoutManager(this@MainActivity)
            rv.adapter = adapter

            fav.setOnClickListener {
                startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
            }

            darkMode.setOnClickListener {
                startActivity(Intent(this@MainActivity, DarkModeActivity::class.java))
            }

            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchBar.setText(searchView.text)
                mainViewModel.getAllUsers(searchView.text.toString()).observe(this@MainActivity) {
                    adapter.submitList(it)
                }
                searchView.hide()
                false
            }

            mainViewModel.getLoading().observe(this@MainActivity) {
                showLoading(it)
            }
        }
    }

    private fun showLoading(it: Boolean) {
        bind.progressBar.visibility = if (it) View.VISIBLE else View.GONE
    }
}