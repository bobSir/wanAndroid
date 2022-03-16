package com.bob.wanandroid.topic.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * created by cly on 2022/3/16
 */
class TopicPagerAdapter(
    fragmentManager: FragmentManager,
) : FragmentPagerAdapter(fragmentManager) {
    private var fragments = arrayListOf<Fragment>()
    private var titles = arrayListOf<String>()

    fun setData(fragments: ArrayList<Fragment>, titles: ArrayList<String>) {
        this.fragments = fragments
        this.titles = titles
        this.notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }
}