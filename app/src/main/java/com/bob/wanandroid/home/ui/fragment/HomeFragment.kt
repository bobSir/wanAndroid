package com.bob.wanandroid.home.ui.fragment

import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.bob.base.ui.BaseFragment
import com.bob.lutil.log.BobLog
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

    private lateinit var homePagerAdapter: HomePagerAdapter

    override fun initView() {
        initViewPager()
        initTabLayout()
    }

    override fun subscribeUi() {}

    private fun initViewPager() {
        homePagerAdapter = HomePagerAdapter(
            this,
            arrayListOf(HotArticleFragment(), QAFragment())
//            arrayListOf(QAFragment(), HotArticleFragment())
        )
        home_vp.adapter = homePagerAdapter
        home_vp.registerOnPageChangeCallback(object : OnPageChangeCallback() {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                BobLog.d("@cly", "onPageSelected - $position")
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })
    }

    private fun initTabLayout() {
        TabLayoutMediator(home_tab, home_vp) { tab, position ->
            tab.text = title[position]
        }.attach()
    }
}