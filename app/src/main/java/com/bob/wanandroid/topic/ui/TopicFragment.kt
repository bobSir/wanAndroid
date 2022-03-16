package com.bob.wanandroid.topic.ui

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bob.base.ui.BaseFragment
import com.bob.base.ui.vpAdapter.CommonPagerAdapter
import com.bob.common.ext.observe
import com.bob.wanandroid.R
import com.bob.wanandroid.topic.vm.TopicViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_topic.*


/**
 * created by cly on 2022/1/25
 */
class TopicFragment : BaseFragment() {

    private val title = arrayListOf<String>()
    private val fragments = arrayListOf<Fragment>()

    private val topicViewModel: TopicViewModel by viewModels()
    private lateinit var adapter: CommonPagerAdapter

    override val layoutId: Int = R.layout.fragment_topic

    override fun initView() {
        adapter = CommonPagerAdapter(this, fragments)
        vp_topic.adapter = adapter
        TabLayoutMediator(tab_topic, vp_topic) { tab, position ->
            tab.text = title[position]
        }.attach()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun subscribeUi() {
        topicViewModel.fetchChapters()
        observe(topicViewModel.chapters) { chapters ->
            chapters.forEach {
                title.add(it.name)
                fragments.add(TopicTabFragment.newInstance(it.id))
            }
            adapter.notifyDataSetChanged()
        }
    }
}