package com.bob.wanandroid.home.ui.fragment

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.bob.base.ui.BaseFragment
import com.bob.common.ext.observe
import com.bob.wanandroid.R
import com.bob.wanandroid.home.ui.adapter.HotArticleLoadStateAdapter
import com.bob.wanandroid.home.ui.adapter.HotArticlePagedAdapter
import com.bob.wanandroid.home.vm.HomeViewModel
import com.bob.wanandroid.webArticle.ui.WebArticleActivity
import kotlinx.android.synthetic.main.fragment_hot_article.*

/**
 * created by cly on 2022/1/25
 */
class HotArticleFragment : BaseFragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private val mAdapter: HotArticlePagedAdapter = HotArticlePagedAdapter()

    override val layoutId: Int = R.layout.fragment_hot_article

    override fun initView() {
        btn_retry.setOnClickListener { mAdapter.retry() }
        refresh_hot.setOnRefreshListener {
            mAdapter.refresh()
        }
        rv_hot.adapter = mAdapter
        rv_hot.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rv_hot.adapter = mAdapter.withLoadStateHeaderAndFooter(
            header = HotArticleLoadStateAdapter { mAdapter.retry() },
            footer = HotArticleLoadStateAdapter { mAdapter.retry() }
        )
        mAdapter.addLoadStateListener { loadState ->
            val isListEmpty = loadState.refresh is LoadState.NotLoading && mAdapter.itemCount == 0
            rv_hot.isVisible = !isListEmpty
            pb_loading.isVisible = loadState.source.refresh is LoadState.Loading && mAdapter.itemCount == 0
            btn_retry.isVisible = loadState.source.refresh is LoadState.Error
            refresh_hot.isRefreshing = loadState.source.refresh is LoadState.Loading && mAdapter.itemCount > 0
        }
        mAdapter.observeItemEvent().observe(this) { link ->
            WebArticleActivity.launch(requireActivity(), link)
        }
    }

    override fun subscribeUi() {
        observe(homeViewModel.newResult) {
            mAdapter.submitData(lifecycle, it)
        }
    }
}