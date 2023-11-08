package com.fuadhev.task3.common.utils

import android.view.View

object Extensions {


    fun View.visible(){
        this.visibility = View.VISIBLE
    }

    fun View.gone(){
        this.visibility = View.GONE
    }

    fun List<View>.goneEach(){
        this.forEach{
            it.gone()
        }
    }

    fun List<View>.visibleEach(){
        this.forEach{
            it.visible()
        }
    }
}