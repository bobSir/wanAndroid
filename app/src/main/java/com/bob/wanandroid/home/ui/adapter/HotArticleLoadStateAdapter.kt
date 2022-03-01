package com.bob.wanandroid.home.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bob.wanandroid.R

/**
 * created by cly on 2022/1/27
 */
class HotArticleLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_load_state_footer_view, parent, false
        )
        return LoadStateViewHolder(view, retry)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}

class LoadStateViewHolder(view: View, retry: () -> Unit) : RecyclerView.ViewHolder(view) {
    private val tvTips: TextView = view.findViewById(R.id.tv_tips)
    private val pbLoading: ProgressBar = view.findViewById(R.id.pb_loading)

    init {
        tvTips.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            tvTips.text = "网络异常"
        }
        tvTips.isVisible = loadState is LoadState.Error
        pbLoading.isVisible = loadState is LoadState.Loading
    }
}