package com.bob.system.component

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.IComponent
import com.bob.common.router.SystemRouter
import com.bob.system.ui.view.SystemFragment

/**
 * created by cly on 2020/12/1
 */
class ComponentSystem : IComponent {

    override fun getName(): String {
        return SystemRouter.componentName
    }

    override fun onCall(cc: CC?): Boolean {
        when (cc?.actionName) {
            SystemRouter.createSystemFragment -> createSystemFragment(cc);
        }
        return false
    }

    private fun createSystemFragment(cc: CC?) {
        CC.sendCCResult(cc?.callId, CCResult.successWithNoKey(SystemFragment.newInstance()))
    }
}