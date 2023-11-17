package com.fuadhev.task3.ui.saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fuadhev.task3.R
import com.fuadhev.task3.common.base.BaseFragment
import com.fuadhev.task3.common.utils.Extensions.gone
import com.fuadhev.task3.common.utils.Extensions.showMessage
import com.fuadhev.task3.common.utils.Extensions.visible
import com.fuadhev.task3.databinding.FragmentSavedBinding
import com.fuadhev.task3.ui.home.adapter.NewsAdapter
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedFragment : BaseFragment<FragmentSavedBinding>(FragmentSavedBinding::inflate) {

    private val viewModel by viewModels<SavedViewModel>()
    private val newsAdapter by lazy {
        NewsAdapter()
    }

    override fun observeEvents() {
        lifecycleScope.launch {
            viewModel.savedState.flowWithLifecycle(lifecycle).collectLatest {
                handleState(it)
            }
        }
//        viewModel.savedState.observe(viewLifecycleOwner) {
//            handleState(it)
//        }
    }

    override fun setupListeners() {
        newsAdapter.onClick = {
            findNavController().navigate(
                SavedFragmentDirections.actionSavedFragmentToDetailFragment(
                    it
                )
            )
        }
    }

    override fun onCreateFinish() {
        binding.rvSaved.adapter = newsAdapter
        viewModel.getSaves()
    }

    private fun handleState(state: SavedUiState) {
        with(binding) {
            when (state) {
                is SavedUiState.Loading -> {
                    progressBar.visible()
                }
                is SavedUiState.SuccessSavedData -> {
                    progressBar.gone()
                    newsAdapter.submitList(state.list.reversed())
                }

                is SavedUiState.Error -> {
                    progressBar.gone()
                    requireActivity().showMessage(state.message, FancyToast.ERROR)
                }
            }
        }

    }

}