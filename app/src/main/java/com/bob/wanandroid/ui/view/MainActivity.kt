package com.bob.wanandroid.ui.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import com.billy.cc.core.component.CC
import com.bob.base.ui.base.BaseActivity
import com.bob.common.router.FindRouter
import com.bob.wanandroid.R
import com.bob.wanandroid.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var fragments: Map<Int, Fragment?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        fragments = mapOf(
            R.id.home to createHomeFragment()
        )
    }

    private fun createHomeFragment(): Fragment? {
        var fragment: Fragment? = null
        CC.obtainBuilder(FindRouter.componentName)
            .setActionName(FindRouter.createFindFragment)
            .build().callAsyncCallbackOnMainThread { _, result ->
                if (result.isSuccess) {
                    fragment = result.getDataItemWithNoKey()
                }
            }
        return fragment
    }
}