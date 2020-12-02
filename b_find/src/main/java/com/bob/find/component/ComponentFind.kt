package com.bob.find.component

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.IComponent
import com.bob.common.router.FindRouter
import com.bob.find.ui.view.DiscoveryFragment

/**
 * created by cly on 2020/12/1
 */
class ComponentFind : IComponent {

    override fun getName(): String {
        return FindRouter.componentName
    }

    override fun onCall(cc: CC?): Boolean {
        when (cc?.actionName) {
            FindRouter.createFindFragment -> createFindFragment(cc);
        }
        return false
    }

    private fun createFindFragment(cc: CC?) {
        CC.sendCCResult(cc?.callId, CCResult.successWithNoKey(DiscoveryFragment.newInstance()))
    }
}