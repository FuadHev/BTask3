package com.fuadhev.task3.ui.home

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fuadhev.task3.R
import com.fuadhev.task3.common.base.BaseFragment
import com.fuadhev.task3.common.utils.Extensions.gone
import com.fuadhev.task3.common.utils.Extensions.visible
import com.fuadhev.task3.databinding.FragmentHomeBinding
import com.fuadhev.task3.ui.home.adapter.NewsAdapter
import com.fuadhev.task3.ui.home.adapter.TopNewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {


    private val viewModel by viewModels<HomeViewModel>()

    private val topNewsAdapter by lazy {
        TopNewsAdapter()
    }
    private val newsAdapter by lazy {
        NewsAdapter()
    }
    private var lang="en"
    private var searchText=""





    override fun observeEvents() {
        viewModel.homeState.observe(viewLifecycleOwner) {
            handleState(it)
        }
        viewModel.selectedLanguage.observe(viewLifecycleOwner){
            viewModel.getTopNews(it)
            lang=it
            viewModel.searchNeews(lang,searchText)
            binding.btnLanguage.text=it.toUpperCase(Locale.ROOT)
        }

    }

    override fun onCreateFinish() {
        setAdapters()
        viewModel.getLanguage()
        searchNews()

    }

    override fun setupListeners() {



        newsAdapter.onClick={

            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(it))

        }
        topNewsAdapter.onClick={

            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(it))

        }

        binding.btnLanguage.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLanguageFragment())
        }

        binding.refreshScreen.setOnRefreshListener {
             viewModel.getLanguage()
            Handler(Looper.getMainLooper()).postDelayed({
                binding.refreshScreen.isRefreshing = false
            }, 1000)
        }

    }

    private fun searchNews(){
        binding.searchView.doAfterTextChanged {

        }
        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //sorgu limiti tez dolur deye yoruma aldim
//                viewModel.searchViews(lang,s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

                searchText = s.toString()
                viewModel.searchNeews(lang,searchText)

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
//                    newsAdapter.submitList(state.list)
                }
                is HomeUiState.SuccessSearchData->{
                    loading.gone()
                    newsAdapter.submitList(state.list) }

                is HomeUiState.Error->{ loading.gone()}

            }

        }


    }


}