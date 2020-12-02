package com.bob.b_navgation.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bob.b_navgation.R
import com.bob.base.ui.base.BaseFragment

/**
 * created by cly on 2020/11/30
 */
class NavigationFragment : BaseFragment() {
    companion object {
        fun newInstance() = NavigationFragment()
    }

    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.nav_fragment_nav, null, false)
    }
}