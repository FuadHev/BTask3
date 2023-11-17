package com.fuadhev.task3.ui.language

import android.content.res.Configuration
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
import java.util.Locale

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

                setAppLocale(updateSelectedLanguage("en"))

                findNavController().popBackStack()
//                findNavController().navigate(LanguageFragmentDirections.actionLanguageFragmentToHomeFragment())
            }
            binding.langRu.setOnClickListener {
                viewModel.saveLang("ru")
                setAppLocale(updateSelectedLanguage("ru"))

                findNavController().popBackStack()
//                findNavController().navigate(LanguageFragmentDirections.actionLanguageFragmentToHomeFragment())
            }
        }
    }

    private fun setAppLocale(locale: Locale) {
        val resources = requireContext().resources
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    private fun updateSelectedLanguage(languageCode: String?) : Locale {
        return when (languageCode) {
            "en" -> Locale("en")
            "ru" -> Locale("ru")
            else -> Locale("en")
        }
    }

}