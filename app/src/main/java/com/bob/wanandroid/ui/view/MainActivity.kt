package com.bob.wanandroid.ui.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import com.billy.cc.core.component.CC
import com.bob.base.ui.base.BaseActivity
import com.bob.common.router.*
import com.bob.wanandroid.R
import com.bob.wanandroid.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var fragments: Map<Int, Fragment?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        fragments = mapOf(
            R.id.home to createHomeFragment(),
            R.id.system to createSystemFragment(),
            R.id.discovery to createFindFragment(),
            R.id.navigation to createNavigationFragment(),
            R.id.mine to createMineFragment()
        )
        bnv.run {
            setOnNavigationItemSelectedListener { menuItem ->
                showFragment(menuItem.itemId)
                true
            }
            setOnNavigationItemReselectedListener {

            }
        }
    }

    private fun showFragment(itemId: Int) {
        val currentFragment = supportFragmentManager.fragments.find {
            it.isVisible && it in fragments.values
        }
        val targetFragment = fragments[itemId]
        supportFragmentManager.beginTransaction().apply {
            currentFragment?.let {
                if (it.isVisible) hide(it)
            }
            targetFragment?.let {
                if (it.isAdded) show(it) else add(R.id.container, it)
            }
        }.commit()
    }

    private fun createMineFragment(): Fragment? {
        var fragment: Fragment? = null
        CC.obtainBuilder(MineRouter.componentName)
            .setActionName(MineRouter.createMineFragment)
            .build().callAsyncCallbackOnMainThread { _, result ->
                if (result.isSuccess) {
                    fragment = result.getDataItemWithNoKey()
                }
            }
        return fragment
    }

    private fun createNavigationFragment(): Fragment? {
        var fragment: Fragment? = null
        CC.obtainBuilder(NavigationRouter.componentName)
            .setActionName(NavigationRouter.createNavigationFragment)
            .build().callAsyncCallbackOnMainThread { _, result ->
                if (result.isSuccess) {
                    fragment = result.getDataItemWithNoKey()
                }
            }
        return fragment
    }

    private fun createSystemFragment(): Fragment? {
        var fragment: Fragment? = null
        CC.obtainBuilder(SystemRouter.componentName)
            .setActionName(SystemRouter.createSystemFragment)
            .build().callAsyncCallbackOnMainThread { _, result ->
                if (result.isSuccess) {
                    fragment = result.getDataItemWithNoKey()
                }
            }
        return fragment
    }

    private fun createFindFragment(): Fragment? {
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

    private fun createHomeFragment(): Fragment? {
        var fragment: Fragment? = null
        CC.obtainBuilder(HomeRouter.componentName)
            .setActionName(HomeRouter.componentHomeFragment)
            .build().callAsyncCallbackOnMainThread { _, result ->
                if (result.isSuccess) {
                    fragment = result.getDataItemWithNoKey()
                }
            }
        return fragment
    }
}