package com.fuadhev.task3.common.utils

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.fuadhev.task3.R
import com.google.android.material.imageview.ShapeableImageView

object BindingAdapter {

    @BindingAdapter("load_url")
    @JvmStatic
    fun loadUrl(view: ShapeableImageView, url: String?) {
        url?.let {
            Glide.with(view)
                .load(url)
                .placeholder(R.color.white)
                .into(view)
        }
    }

}