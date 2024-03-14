package com.app.githubApp

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.githubApp.adapter.UsersAdapter
import com.app.githubApp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bind : ActivityMainBinding
    private val adapter = UsersAdapter()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bind.rv.layoutManager = LinearLayoutManager(this@MainActivity)
        bind.rv.adapter = adapter

        with(bind) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchBar.setText(searchView.text)
                mainViewModel.getListUsers(searchView.text.toString()).observe(this@MainActivity){
                    adapter.submitList(it)
                }
                searchView.hide()
                false
            }

            mainViewModel.isLoading.observe(this@MainActivity){
                showLoading(it)
            }
        }
    }

    private fun showLoading(it: Boolean) {
        bind.progressBar.visibility = if (it) View.VISIBLE else View.GONE
    }
}