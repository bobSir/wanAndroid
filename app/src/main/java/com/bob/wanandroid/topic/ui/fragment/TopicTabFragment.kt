package com.bob.wanandroid.topic.ui.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.bob.base.ui.BaseFragment
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

    private val adapter = ChapterArticleAdapter(arrayListOf())

    override val layoutId: Int = R.layout.fragment_topic_tab

    override fun initParams() {
        val id = arguments?.getInt(KEY_TITLE)
        id?.let { viewModel.fetchChapterArticle(it) }
    }

    override fun initView() {
        rv_topic_tab.adapter = adapter
        rv_topic_tab.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        adapter.setOnItemClickListener { adapter, _, position ->
            val article: Article = adapter.getItem(position) as Article
            WebArticleActivity.launch(requireActivity(), article.link)
        }
    }

    override fun subscribeUi() {
        observe(viewModel.articles) {
            //如果是加载第一页数据，用setData
            adapter.setList(it)
//            adapter.addData()  不是第一页 用add
        }
    }
}