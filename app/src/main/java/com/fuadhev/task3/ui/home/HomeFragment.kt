package com.fuadhev.task3.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.fuadhev.task3.R
import com.fuadhev.task3.common.base.BaseFragment
import com.fuadhev.task3.common.utils.Extensions.gone
import com.fuadhev.task3.common.utils.Extensions.visible
import com.fuadhev.task3.databinding.FragmentHomeBinding
import com.fuadhev.task3.ui.home.adapter.NewsAdapter
import com.fuadhev.task3.ui.home.adapter.TopNewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {


    private val viewModel by viewModels<HomeViewModel>()

    private val topNewsAdapter by lazy {
        TopNewsAdapter()
    }
    private val newsAdapter by lazy {
        NewsAdapter()
    }


    override fun observeEvents() {
        viewModel.homeState.observe(viewLifecycleOwner) {
            handleState(it)
        }
    }

    override fun onCreateFinish() {
        viewModel.getTopNews("en")
        setAdapters()
    }

    override fun setupListeners() {

        newsAdapter.onClick={

            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(it))

        }
        topNewsAdapter.onClick={

            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(it))

        }

        searchNews()
    }




    private fun searchNews(){
        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchViews("en",s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

                val searchText = s.toString()
                viewModel.searchViews("en",searchText)

            }
        })

    }

    private fun setAdapters() {
        binding.rvTopNews.adapter=topNewsAdapter
        binding.rvNews.adapter=newsAdapter

    }

    private fun handleState(state: HomeUiState) {
        with(binding){

            when(state){
                is HomeUiState.Loading->{ loading.visible()  }

                is HomeUiState.SuccessTopNewsData->{
                    loading.gone()
                    topNewsAdapter.submitList(state.list)
                    newsAdapter.submitList(state.list)
                }
                is HomeUiState.SuccessSearchData->{
                    loading.gone()
                    newsAdapter.submitList(state.list) }

                is HomeUiState.Error->{ loading.gone() }
            }

        }


    }


}