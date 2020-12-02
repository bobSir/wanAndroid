package com.bob.find.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bob.base.ui.base.BaseFragment
import com.bob.find.R

/**
 * created by cly on 2020/11/30
 */
class DiscoveryFragment : BaseFragment() {
    companion object {
        fun newInstance(): DiscoveryFragment {
            val args = Bundle()
            val fragment = DiscoveryFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.find_fragment_find, null, false)
        return view
    }
}