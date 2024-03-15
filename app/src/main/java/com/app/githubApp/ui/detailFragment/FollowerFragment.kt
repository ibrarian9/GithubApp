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
import com.app.githubApp.databinding.FragmentFollowerBinding
import com.app.githubApp.ui.detailActivity.DetailViewModel

class FollowerFragment : Fragment() {

    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }
    private val adapter = FollowerAdapter()
    private lateinit var bind: FragmentFollowerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentFollowerBinding.inflate(inflater, container, false)

        with(bind){
            rvFollower.layoutManager = LinearLayoutManager(requireActivity())
            rvFollower.adapter = adapter

            val dataNama = arguments?.getString(NAMA)

            detailViewModel.getFollower(dataNama!!).observe(viewLifecycleOwner){
                adapter.submitList(it)
            }

            detailViewModel.getLoading().observe(viewLifecycleOwner) {
                showLoading(it)
            }
        }
        return bind.root
    }

    private fun showLoading(it: Boolean) {
        bind.progressBar.visibility = if (it) View.VISIBLE else View.GONE
    }

    companion object {

        fun newInstance(dataNama: String): FollowerFragment {
            val fragment = FollowerFragment()
            val args = Bundle()
            args.putString(NAMA, dataNama)
            fragment.arguments = args
            return fragment
        }

        const val NAMA = "nama"
    }
}