package com.bob.wanandroid.user.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bob.base.ui.base.BaseFragment
import com.bob.wanandroid.user.R

/**
 * created by cly on 2020/11/30
 */
class MineFragment : BaseFragment() {
    companion object {
        fun newInstance() = MineFragment()
    }

    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.user_fargment_mine, null, false)
    }
}