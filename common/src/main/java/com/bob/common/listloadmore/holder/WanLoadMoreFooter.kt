package com.bob.common.listloadmore.holder

import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.bob.common.R
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView
import com.takwolf.android.hfrecyclerview.loadmorefooter.LoadMoreFooter

class WanLoadMoreFooter(view: View) : LoadMoreFooter(view) {
    companion object {
        fun create(recyclerView: HeaderAndFooterRecyclerView): WanLoadMoreFooter {
            val view = LayoutInflater.from(recyclerView.context).inflate(R.layout.layout_footer_loadmore, recyclerView.footerViewContainer, false)
            return WanLoadMoreFooter(view)
        }
    }

    private val tvText: TextView = view.findViewById(R.id.tv_text)
    private val loadingBar: ProgressBar = view.findViewById(R.id.loading_bar)

    init {
        tvText.setOnClickListener {
            checkDoLoadMore()
        }
        preloadOffset = 10
    }

    override fun onUpdateViews(footerView: View, @State state: Int) {
        when (state) {
            STATE_DISABLED -> {
                loadingBar.visibility = View.INVISIBLE
                tvText.visibility = View.INVISIBLE
                tvText.text = null
                tvText.isClickable = false
            }
            STATE_LOADING -> {
                loadingBar.visibility = View.VISIBLE
                tvText.visibility = View.INVISIBLE
                tvText.text = null
                tvText.isClickable = false
            }
            STATE_FINISHED -> {
                loadingBar.visibility = View.INVISIBLE
                tvText.visibility = View.VISIBLE
                tvText.text = "没有更多内容了"
                tvText.isClickable = false
            }
            STATE_ENDLESS -> {
                loadingBar.visibility = View.INVISIBLE
                tvText.visibility = View.VISIBLE
                tvText.text = null
                tvText.isClickable = true
            }
            STATE_FAILED -> {
                loadingBar.visibility = View.INVISIBLE
                tvText.visibility = View.VISIBLE
                tvText.text = "加载失败，点击重试"
                tvText.isClickable = true
            }
        }
    }
}
