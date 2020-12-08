package com.bob.wanandroid.ui.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
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
        if (savedInstanceState == null) {
            val initialItemId = R.id.home
            bnv.selectedItemId = initialItemId
            showFragment(initialItemId)
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
        val fragment: Fragment?
        val call = CC.obtainBuilder(MineRouter.componentName)
            .setActionName(MineRouter.createMineFragment)
            .build()
            .call()
        fragment = call.getDataItemWithNoKey();
        return fragment
    }

    private fun createNavigationFragment(): Fragment? {
        val fragment: Fragment?
        val call = CC.obtainBuilder(NavigationRouter.componentName)
            .setActionName(NavigationRouter.createNavigationFragment)
            .build()
            .call()
        fragment = call.getDataItemWithNoKey()
        return fragment
    }

    private fun createSystemFragment(): Fragment? {
        val fragment: Fragment?
        val call: CCResult = CC.obtainBuilder(SystemRouter.componentName)
            .setActionName(SystemRouter.createSystemFragment)
            .build()
            .call()
        fragment = call.getDataItemWithNoKey()
        return fragment
    }

    private fun createFindFragment(): Fragment? {
        val fragment: Fragment?
        val call: CCResult = CC.obtainBuilder(FindRouter.componentName)
            .setActionName(FindRouter.createFindFragment)
            .build()
            .call()
        fragment = call.getDataItemWithNoKey()
        return fragment
    }

    private fun createHomeFragment(): Fragment? {
        val fragment: Fragment?
        val call = CC.obtainBuilder(HomeRouter.componentName)
            .setActionName(HomeRouter.componentHomeFragment)
            .build()
            .call()
        fragment = call.getDataItemWithNoKey()
        return fragment
    }
}