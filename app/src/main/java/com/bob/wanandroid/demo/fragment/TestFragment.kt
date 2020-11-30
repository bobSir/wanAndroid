package com.bob.wanandroid.demo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener

/**
 * created by cly on 2020/11/27
 */
class TestFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun demo1() {
        //接收数据
        parentFragmentManager.setFragmentResultListener("key", this,
            FragmentResultListener { requestKey, result ->
                result.getString("key")
            })
        //传递数据
        parentFragmentManager.setFragmentResult("key", bundleOf());
    }
}