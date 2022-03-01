package com.bob.wanandroid

import com.bob.base.ui.BaseActivity
import com.bob.wanandroid.home.ui.HomeFragment
import com.bob.wanandroid.navigation.ui.NavigationFragment
import com.bob.wanandroid.topic.ui.TopicFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override val layoutId: Int = R.layout.activity_main

    override fun subscribeUi() {
        initVpContainer()
        initNavigation()
    }

    private fun initVpContainer() {
        vp_container.adapter = MainPagerAdapter(
            this,
            listOf(HomeFragment(), TopicFragment(), NavigationFragment())
        )
        vp_container.offscreenPageLimit = 2
        vp_container.isUserInputEnabled = false
    }

    private fun initNavigation() {
        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> vp_container.setCurrentItem(0, false)
                R.id.nav_topic -> vp_container.setCurrentItem(1, false)
                R.id.nav_nagivation -> vp_container.setCurrentItem(2, false)
            }
            true
        }
    }
}