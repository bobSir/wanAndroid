package com.bob.wanandroid.home.ui

import com.bob.base.ui.BaseFragment
import com.bob.wanandroid.R
import com.bob.wanandroid.home.ui.adapter.HomePagerAdapter
import com.bob.wanandroid.home.ui.fragment.HotArticleFragment
import com.bob.wanandroid.home.ui.fragment.QAFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * created by cly on 2022/1/25
 */
class HomeFragment : BaseFragment() {
    private val title = arrayOf("热门", "问答")

    override val layoutId: Int = R.layout.fragment_home

    lateinit var homePagerAdapter: HomePagerAdapter

    override fun initView() {
        initViewPager()
        initTabLayout()
    }

    override fun subscribeUi() {}

    private fun initViewPager() {
        homePagerAdapter = HomePagerAdapter(
            this,
            arrayListOf(HotArticleFragment(), QAFragment())
        )
        home_vp.adapter = homePagerAdapter
    }

    private fun initTabLayout() {
        TabLayoutMediator(home_tab, home_vp) { tab, position ->
            tab.text = title[position]
        }.attach()
    }
}