package com.scorp.studycase.util

import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener() : RecyclerView.OnScrollListener() {

    abstract fun loadMoreItems()
    abstract fun refreshPage()

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
            loadMoreItems()
        }
        if (!recyclerView.canScrollVertically(-1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
            refreshPage()
        }
    }
}