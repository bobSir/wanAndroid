package com.bob.b_navgation.component

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.IComponent
import com.bob.b_navgation.ui.view.NavigationFragment
import com.bob.common.router.NavigationRouter

/**
 * created by cly on 2020/12/1
 */
class ComponentNavigation : IComponent {

    override fun getName(): String {
        return NavigationRouter.componentName
    }

    override fun onCall(cc: CC?): Boolean {
        when (cc?.actionName) {
            NavigationRouter.createNavigationFragment -> createNavigationFragment(cc);
        }
        return false
    }

    private fun createNavigationFragment(cc: CC?) {
        CC.sendCCResult(cc?.callId, CCResult.successWithNoKey(NavigationFragment.newInstance()))
    }
}