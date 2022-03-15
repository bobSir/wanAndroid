package com.bob.wanandroid.topic.ui

import androidx.fragment.app.Fragment
import com.bob.base.ui.BaseFragment
import com.bob.base.ui.vpAdapter.CommonPagerAdapter
import com.bob.wanandroid.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_topic.*

/**
 * created by cly on 2022/1/25
 */
class TopicFragment : BaseFragment() {

    private val title = arrayListOf<String>()

    override val layoutId: Int = R.layout.fragment_topic

    override fun initView() {
        val commonPagerAdapter = CommonPagerAdapter(this, initFragments())
        vp_topic.adapter = commonPagerAdapter
        TabLayoutMediator(tab_topic, vp_topic) { tab, position ->
            tab.text = title[position]
        }.attach()
    }

    override fun subscribeUi() {
    }

    private fun initFragments(): List<Fragment> {
        val fragments = arrayListOf<Fragment>()
        for (i in 0..9) {
            title.add(i.toString())
            fragments.add(TopicTabFragment.newInstance(title[i]))
        }
        return fragments
    }
}