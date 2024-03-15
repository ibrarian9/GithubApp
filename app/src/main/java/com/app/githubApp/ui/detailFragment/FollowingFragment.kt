package com.app.githubApp.ui.detailFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.githubApp.ui.ViewModelFactory
import com.app.githubApp.adapter.FollowerAdapter
import com.app.githubApp.databinding.FragmentFollowingBinding
import com.app.githubApp.ui.detailActivity.DetailViewModel

class FollowingFragment : Fragment() {

    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }
    private val adapter = FollowerAdapter()
    private lateinit var bind: FragmentFollowingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentFollowingBinding.inflate(inflater, container, false)

        val dataNama = arguments?.getString(NAMA)

        with(bind) {
            rvFollowing.layoutManager = LinearLayoutManager(requireActivity())
            rvFollowing.adapter = adapter

            detailViewModel.getFollowing(dataNama!!).observe(viewLifecycleOwner){
                adapter.submitList(it)
            }

            detailViewModel.getLoading().observe(viewLifecycleOwner){
                showLoading(it)
            }
        }

        return bind.root
    }

    private fun showLoading(it: Boolean) {
        bind.progressBar.visibility = if (it) View.VISIBLE else View.GONE
    }

    companion object {

        fun newInstance(dataNama: String): FollowingFragment {
            val fragment = FollowingFragment()
            val args = Bundle()
            args.putString(NAMA, dataNama)
            fragment.arguments = args
            return fragment
        }

        const val NAMA = "nama"
    }
}