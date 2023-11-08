package com.fuadhev.task3.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fuadhev.task3.common.utils.GenericDiffUtil
import com.fuadhev.task3.data.network.dto.Article
import com.fuadhev.task3.databinding.ItemNewsBinding
import com.fuadhev.task3.databinding.ItemTopNewsBinding
import com.fuadhev.task3.domain.model.NewsUiModel
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


private val diffUtil = GenericDiffUtil<NewsUiModel>(
    myItemsTheSame = { oldItem, newItem -> oldItem.title == newItem.title },
    myContentsTheSame = { oldItem, newItem -> oldItem == newItem }
)

class NewsAdapter : ListAdapter<NewsUiModel, NewsAdapter.NewsViewHolder>(diffUtil) {
    var onClick : (NewsUiModel) -> Unit = {}
    inner class NewsViewHolder(val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewsUiModel) {
            with(binding) {

                newsData=item
                binding.txtTitle.text= item.title?.let { shortAuthor(it, 80) }


                txtAuthor.text=shortAuthor(item.author?:"Anonym", 18)

                itemNews.setOnClickListener {
                    onClick(item)
                }

                val sdf = SimpleDateFormat("yyyy-MM-dd")
                val date = sdf.parse(item.publishedAt)
                val cal = java.util.Calendar.getInstance()
                if (date != null) {
                    cal.time = date
                }

                val tarih = "${cal.get(java.util.Calendar.YEAR)}-${cal.get(java.util.Calendar.MONTH) + 1}-${cal.get(java.util.Calendar.DAY_OF_MONTH)}"
                binding.txtDate.text=tarih

                executePendingBindings()

            }
        }
    }

    fun shortAuthor(metin: String, maksimumKarakter: Int): String {
        return if (metin.length <= maksimumKarakter) {
            metin
        } else {
            val kisaltilmisMetin = metin.substring(0, maksimumKarakter - 3) + "..."
            kisaltilmisMetin
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}