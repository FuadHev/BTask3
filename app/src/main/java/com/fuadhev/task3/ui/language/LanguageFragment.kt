package com.fuadhev.task3.ui.language

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fuadhev.task3.R
import com.fuadhev.task3.common.base.BaseFragment
import com.fuadhev.task3.databinding.FragmentLanguageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageFragment : BaseFragment<FragmentLanguageBinding>(FragmentLanguageBinding::inflate) {

    private val viewModel by viewModels<LanguageViewModel>()

    override fun observeEvents() {

    }

    override fun onCreateFinish() {

    }

    override fun setupListeners() {
        with(binding){
            binding.btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            binding.langEn.setOnClickListener {
                viewModel.saveLang("en")
                findNavController().popBackStack()
            }
            binding.langRu.setOnClickListener {
                viewModel.saveLang("ru")
                findNavController().popBackStack()

            }
        }
    }

}