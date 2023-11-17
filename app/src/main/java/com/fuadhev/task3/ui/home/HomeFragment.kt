package com.fuadhev.task3.ui.home

import android.content.res.Configuration
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
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
        lifecycleScope.launch {
            viewModel.homeState.flowWithLifecycle(lifecycle).collectLatest {
                handleState(it)
            }
        }
        lifecycleScope.launch {
            viewModel.selectedLanguage.flowWithLifecycle(lifecycle).collectLatest {
                viewModel.getTopNews(it)
                lang=it
                setAppLocale(updateSelectedLanguage(it))
                viewModel.searchNews(lang,searchText)
                binding.btnLanguage.text=it.toUpperCase(Locale.ROOT)
            }
        }

//        viewModel.selectedLanguage.observe(viewLifecycleOwner){
//
//        }

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
    }

    private fun searchNews(){
        binding.searchView.doAfterTextChanged {
            searchText = it.toString()
            viewModel.searchNews(lang,searchText)
        }
    }

    private fun setAdapters() {
        binding.rvTopNews.adapter=topNewsAdapter
        binding.rvNews.adapter=newsAdapter
    }

    private fun setAppLocale(locale: Locale) {
        val resources = requireContext().resources
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    private fun updateSelectedLanguage(languageCode: String?) :Locale{
        return when (languageCode) {
            "en" -> Locale("en")
            "ru" -> Locale("ru")
            else -> Locale("en")
        }
    }

    private fun handleState(state: HomeUiState) {
        with(binding){

            when(state){
                is HomeUiState.Loading->{ loading.visible()  }

                is HomeUiState.SuccessTopNewsData->{
                    loading.gone()
                    topNewsAdapter.submitList(state.list)
                }
                is HomeUiState.SuccessSearchData->{
                    loading.gone()
                    newsAdapter.submitList(state.list) }

                is HomeUiState.Error->{ loading.gone()}

            }

        }


    }


}