package com.fuadhev.task3.ui.detail

import android.content.Intent
import android.net.Uri
import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.fuadhev.task3.R
import com.fuadhev.task3.common.base.BaseFragment
import com.fuadhev.task3.databinding.FragmentDetailBinding
import com.fuadhev.task3.domain.mapper.Mapper.toSavedDTO
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val args by navArgs<DetailFragmentArgs>()
    private val viewModel by viewModels<DetailViewModel>()
    private var isSaved: Boolean = false

    override fun observeEvents() {
        args.newsDetail.title?.let {
            viewModel.isNewsSaved(it) {
                isSaved = it
            }
        }
        viewModel.detailState.observe(viewLifecycleOwner){
            handleState(it)
        }

    }

    override fun setupListeners() {
        binding.txtReadMore.setOnClickListener {
            val uri = Uri.parse(args.newsDetail.url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    override fun onCreateFinish() {
        val newsDetail = args.newsDetail
        with(binding) {
            newsData = newsDetail
            btnBackTxt.text = shortTitle(newsDetail.title.toString(), 11)
            icMore.setOnClickListener {
                popUpMenu(it)
            }
        }
    }
    private fun handleState(state:DetailUiState){
        when(state){
            is DetailUiState.Loading->{   }
            is DetailUiState.SuccessSaveNews->{   }
            is DetailUiState.SuccessDeleteSave->{  }
            is DetailUiState.Error->{   }

        }

    }

    private fun popUpMenu(view: View) {
        val popupMenu = PopupMenu(requireActivity(), view)

        popupMenu.menuInflater.inflate(R.menu.more_menu, popupMenu.menu)
        popupMenu.gravity = Gravity.END
        val popmenu = popupMenu.menu.findItem(R.id.menu_save)
        if (isSaved) popmenu.setIcon(R.drawable.ic_selected_saved) else popmenu.setIcon(R.drawable.ic_unselected_saved)


        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_save -> {
                    isSaved = if (isSaved){
                        viewModel.deleteSaves(args.newsDetail.toSavedDTO())
                        false
                    }else{
                        viewModel.addSaves(args.newsDetail.toSavedDTO())
                        true
                    }
                    true
                }

                R.id.menu_share -> {
                    true
                }

                else -> false
            }
        }

        try {
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenu)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menu, true)
        } catch (e: Exception) {

            e.printStackTrace()
        } finally {
            popupMenu.show()

        }
    }

    private fun shortTitle(metin: String, maksimumKarakter: Int): String {
        return if (metin.length <= maksimumKarakter) {
            metin
        } else {
            val kisaltilmisMetin = metin.substring(0, maksimumKarakter - 3) + "..."
            kisaltilmisMetin
        }
    }
}