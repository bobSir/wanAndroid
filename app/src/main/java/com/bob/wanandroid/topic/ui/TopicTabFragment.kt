package com.bob.wanandroid.topic.ui

import android.os.Bundle
import com.bob.base.ui.BaseFragment
import com.bob.wanandroid.R
import kotlinx.android.synthetic.main.fragment_topic_tab.*

/**
 * created by cly on 2022/3/15
 */
class TopicTabFragment : BaseFragment() {

    companion object {
        private const val KEY_TITLE = "key_title"

        fun newInstance(title: String): TopicTabFragment {
            val bundle = Bundle()
            bundle.putString(KEY_TITLE,title)
            val topicTabFragment = TopicTabFragment()
            topicTabFragment.arguments = bundle
            return topicTabFragment
        }
    }

    override val layoutId: Int = R.layout.fragment_topic_tab

    override fun initView() {
        val string = arguments?.getString(KEY_TITLE)
        tv_topic_tab.text = string
    }

    override fun subscribeUi() {
    }

}