package com.bob.wanandroid.topic.ui.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.bob.base.ui.BaseFragment
import com.bob.common.customView.chadPagingView.CustomLoadMoreView
import com.bob.common.ext.observe
import com.bob.lnetwork.entity.Article
import com.bob.wanandroid.R
import com.bob.wanandroid.topic.ui.adapter.ChapterArticleAdapter
import com.bob.wanandroid.topic.vm.TopicViewModel
import com.bob.wanandroid.webArticle.ui.WebArticleActivity
import kotlinx.android.synthetic.main.fragment_topic_tab.*

/**
 * created by cly on 2022/3/15
 */
class TopicTabFragment : BaseFragment() {

    companion object {
        private const val KEY_TITLE = "key_title"

        fun newInstance(id: Int): TopicTabFragment {
            val bundle = Bundle()
            bundle.putInt(KEY_TITLE, id)
            val topicTabFragment = TopicTabFragment()
            topicTabFragment.arguments = bundle
            return topicTabFragment
        }
    }

    private val viewModel: TopicViewModel by viewModels()

    private var mId = 0

    private var pagingInfo = PagingInfo()

    private val adapter = ChapterArticleAdapter(arrayListOf())

    override val layoutId: Int = R.layout.fragment_topic_tab

    override fun initParams() {
        mId = arguments?.getInt(KEY_TITLE) ?: 0
    }

    override fun initView() {
        //refresh
        refresh_topic.setOnRefreshListener {
            refresh()
        }
        //adapter
        rv_topic_tab.adapter = adapter
        rv_topic_tab.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        adapter.setOnItemClickListener { adapter, _, position ->
            val article: Article = adapter.getItem(position) as Article
            WebArticleActivity.launch(requireActivity(), article.link)
        }
        val loadMoreModule = adapter.loadMoreModule
        loadMoreModule.loadMoreView = CustomLoadMoreView()
        loadMoreModule.setOnLoadMoreListener {
            loadMore()
        }
        loadMoreModule.isAutoLoadMore = true
        loadMoreModule.isEnableLoadMoreIfNotFullPage = false
    }

    override fun subscribeUi() {
        refresh()
        observe(viewModel.articles) {
            adapter.loadMoreModule.isEnableLoadMore = true
            //如果是加载第一页数据，用setData
            if (pagingInfo.isFirstPage()) {
                refresh_topic.isRefreshing = false
                adapter.setList(it)
            } else {
                //不是第一页 用add
                adapter.addData(it)
                adapter.loadMoreModule.loadMoreComplete()
            }
        }
    }

    private fun refresh() {
        refresh_topic.isRefreshing = true
        adapter.loadMoreModule.isEnableLoadMore = false
        pagingInfo.reset()
        viewModel.fetchChapterArticle(mId, pagingInfo.pageIndex)
    }

    private fun loadMore() {
        pagingInfo.nextPage()
        viewModel.fetchChapterArticle(mId, pagingInfo.pageIndex)
    }

    class PagingInfo {
        var pageIndex = 46

        fun nextPage() {
            pageIndex++
        }

        fun reset() {
            pageIndex = 46
        }

        fun isFirstPage(): Boolean {
            return pageIndex == 46
        }
    }
}