package com.app.githubApp

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.githubApp.adapter.SectionPagerAdapter
import com.app.githubApp.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    private lateinit var bind : ActivityDetailBinding
    private val detailViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dataNama = intent.getStringExtra(NAMA)

        passingDataToFragment(dataNama)

        val pagerAdapter = SectionPagerAdapter(this, dataNama)
        bind.tabView.adapter = pagerAdapter
        TabLayoutMediator(bind.tabs, bind.tabView) { tab, pos ->
            tab.text = resources.getString(TAB_TITLE[pos])
        }.attach()

        detailViewModel.getDetailUser(dataNama!!).observe(this){
            bind.usernameProfile.text = it.login
            bind.namaProfile.text = it.name
            Picasso.get().load(it.avatarUrl).into(bind.potoprofile)

            detailViewModel.getFollower(it.login)
        }

        detailViewModel.isLoading.observe(this){
            showLoading(it)
        }

    }

    private fun passingDataToFragment(nama: String?) {

    }

    private fun showLoading(it: Boolean) {
        bind.progressBar.visibility = if (it) View.VISIBLE else View.GONE
    }

    companion object {
        const val NAMA = "nama"
        private val TAB_TITLE = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}