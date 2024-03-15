package com.app.githubApp.ui.detailActivity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.githubApp.R
import com.app.githubApp.ui.ViewModelFactory
import com.app.githubApp.adapter.SectionPagerAdapter
import com.app.githubApp.databinding.ActivityDetailBinding
import com.app.githubApp.remote.database.GitUser
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    private lateinit var bind : ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }

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

        detailViewModel.getDetailUser(dataNama!!).observe(this){ profile ->
            bind.usernameProfile.text = profile.login
            bind.namaProfile.text = profile.name
            Picasso.get().load(profile.avatarUrl).into(bind.potoprofile)

            val tabTitle = arrayOf(
                "Followers ${profile.followers}",
                "Following ${profile.following}"
            )

            detailViewModel.getFavUserByUsername(profile.login).observe(this@DetailActivity){ gitUser ->
                if (gitUser != null){
                    bind.btnFav.setImageResource(R.drawable.baseline_star_24)
                    bind.btnFav.setOnClickListener {
                        detailViewModel.deleteUsers(gitUser)
                    }
                } else {
                    bind.btnFav.setImageResource(R.drawable.baseline_star_border_24)
                    bind.btnFav.setOnClickListener {
                        val gitUsers = GitUser(
                            name = profile.login,
                            avatarUrl = profile.avatarUrl
                        )
                        detailViewModel.insertUsers(gitUsers)
                    }
                }
            }

            val pagerAdapter = SectionPagerAdapter(this, dataNama)
            bind.tabView.adapter = pagerAdapter
            TabLayoutMediator(bind.tabs, bind.tabView) { tab, pos ->
                tab.text = tabTitle[pos]
            }.attach()
        }

        detailViewModel.getLoading().observe(this){
            showLoading(it)
        }
    }

    private fun showLoading(it: Boolean) {
        bind.progressBar.visibility = if (it) View.VISIBLE else View.GONE
    }

    companion object {
        const val NAMA = "nama"
    }
}