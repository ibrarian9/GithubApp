package com.app.githubApp.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.githubApp.detailFragment.FollowerFragment
import com.app.githubApp.detailFragment.FollowingFragment

class SectionPagerAdapter(activity: AppCompatActivity, private val dataNama: String?) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> FollowerFragment.newInstance(dataNama ?: "")
            1 -> FollowingFragment.newInstance(dataNama ?: "")
            else -> throw IllegalArgumentException("Invalid Pos")
        }
    }

}