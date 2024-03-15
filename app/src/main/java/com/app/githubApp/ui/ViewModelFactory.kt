package com.app.githubApp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.githubApp.data.GitUserRepository
import com.app.githubApp.data.Injection
import com.app.githubApp.ui.darkModeActivity.DarkModeViewModel
import com.app.githubApp.ui.detailActivity.DetailViewModel
import com.app.githubApp.ui.favoriteActivity.FavoriteViewModel
import com.app.githubApp.ui.mainActivity.MainViewModel

class ViewModelFactory private constructor(
    private val repo: GitUserRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repo) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repo) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(repo) as T
            }
            modelClass.isAssignableFrom(DarkModeViewModel::class.java) -> {
                DarkModeViewModel(repo) as T
            }
            else -> throw IllegalArgumentException("Uknown ViewModel Class")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepo(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }


}