package com.app.githubApp.ui.darkModeActivity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.app.githubApp.R
import com.app.githubApp.databinding.ActivityDarkModeBinding
import com.app.githubApp.ui.ViewModelFactory
import kotlinx.coroutines.launch

class DarkModeActivity : AppCompatActivity() {

    private lateinit var bind: ActivityDarkModeBinding
    private val darkModeViewModel by viewModels<DarkModeViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityDarkModeBinding.inflate(layoutInflater)
        setContentView(bind.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        darkModeViewModel.getThemeSet().observe(this){
            if (it){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                bind.swTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                bind.swTheme.isChecked = false
            }
        }

        bind.swTheme.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                darkModeViewModel.saveThemeSet(isChecked)
            }
        }
    }
}