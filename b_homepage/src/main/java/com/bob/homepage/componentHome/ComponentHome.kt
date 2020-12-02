package com.bob.homepage.componentHome

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.IComponent
import com.bob.common.router.HomeRouter
import com.bob.homepage.ui.view.HomeFragment

/**
 * created by cly on 2020/12/2
 */
class ComponentHome : IComponent {

    override fun getName(): String {
        return HomeRouter.componentName
    }

    override fun onCall(cc: CC?): Boolean {
        when (cc?.actionName) {
            HomeRouter.componentHomeFragment -> createHomeFragment(cc)
        }
        return false
    }

    private fun createHomeFragment(cc: CC) {
        CC.sendCCResult(cc.callId, CCResult.successWithNoKey(HomeFragment.newInstance()))
    }
}