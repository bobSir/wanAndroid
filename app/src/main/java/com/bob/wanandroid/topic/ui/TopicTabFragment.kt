package com.bob.wanandroid.topic.ui

import android.os.Bundle
import com.bob.base.ui.BaseFragment
import com.bob.lutil.log.BobLog
import com.bob.wanandroid.R

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

    override val layoutId: Int = R.layout.fragment_topic_tab

    override fun initView() {
        val id = arguments?.getInt(KEY_TITLE)
        BobLog.d(id)
    }

    override fun subscribeUi() {
    }

}