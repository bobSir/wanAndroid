package com.bob.wanandroid.topic.ui.fragment

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bob.base.ui.BaseFragment
import com.bob.common.ext.observe
import com.bob.wanandroid.R
import com.bob.wanandroid.topic.ui.adapter.TopicPagerAdapter
import com.bob.wanandroid.topic.vm.TopicViewModel
import kotlinx.android.synthetic.main.fragment_topic.*


/**
 * created by cly on 2022/1/25
 */
class TopicFragment : BaseFragment() {

    private val topicViewModel: TopicViewModel by viewModels()
    private lateinit var adapter: TopicPagerAdapter

    override val layoutId: Int = R.layout.fragment_topic

    override fun initView() {
        tab_topic.setupWithViewPager(vp_topic)
        adapter = TopicPagerAdapter(childFragmentManager)
        vp_topic.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun subscribeUi() {
        topicViewModel.fetchChapters()
        observe(topicViewModel.chapters) { chapters ->
            val titles = arrayListOf<String>()
            val fragments = arrayListOf<Fragment>()
            chapters.forEach {
                titles.add(it.name)
                fragments.add(TopicTabFragment.newInstance(it.id))
            }
            adapter.setData(fragments, titles)
        }
    }
}