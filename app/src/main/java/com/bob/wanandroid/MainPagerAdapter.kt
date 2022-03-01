package com.bob.wanandroid

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * created by cly on 2022/1/25
 */
class MainPagerAdapter(
    mainActivity: MainActivity,
    private val fragments: List<Fragment>
) : FragmentStateAdapter(mainActivity) {
    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}