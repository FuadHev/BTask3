package com.fuadhev.task3.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fuadhev.task3.common.utils.GenericDiffUtil
import com.fuadhev.task3.data.network.dto.Article
import com.fuadhev.task3.databinding.ItemTopNewsBinding
import com.fuadhev.task3.domain.model.NewsUiModel


private val diffUtil = GenericDiffUtil<NewsUiModel>(
    myItemsTheSame = { oldItem, newItem -> oldItem.title == newItem.title },
    myContentsTheSame = { oldItem, newItem -> oldItem == newItem }
)

class TopNewsAdapter : ListAdapter<NewsUiModel, TopNewsAdapter.TopNewsViewHolder>(diffUtil) {

    var onClick : (NewsUiModel) -> Unit = {}

    inner class TopNewsViewHolder(val binding: ItemTopNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewsUiModel) {
            with(binding) {
                newsData=item
                txtNews.text= item.title?.let { shortTitle(it,60) }


                itemNews.setOnClickListener {
                    onClick(item)
                }
                executePendingBindings()
            }
        }
    }
    fun shortTitle(metin: String, maksimumKarakter: Int): String {
        return if (metin.length <= maksimumKarakter) {
            metin
        } else {
            val kisaltilmisMetin = metin.substring(0, maksimumKarakter - 3) + "..."
            kisaltilmisMetin
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopNewsViewHolder {
        val view = ItemTopNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopNewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

