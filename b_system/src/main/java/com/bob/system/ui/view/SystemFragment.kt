package com.bob.system.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bob.base.ui.base.BaseFragment
import com.bob.system.R

/**
 * created by cly on 2020/11/30
 */
class SystemFragment : BaseFragment() {
    companion object {
        fun newInstance() = SystemFragment()
    }

    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.sys_fragment_system, null, false)
    }
}