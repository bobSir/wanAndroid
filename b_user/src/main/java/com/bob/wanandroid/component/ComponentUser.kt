package com.bob.wanandroid.component

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.IComponent
import com.bob.common.router.MineRouter
import com.bob.wanandroid.user.ui.view.MineFragment

/**
 * created by cly on 2020/12/1
 */
class ComponentUser : IComponent {

    override fun getName(): String {
        return MineRouter.componentName
    }

    override fun onCall(cc: CC?): Boolean {
        when (cc?.actionName) {
            MineRouter.createMineFragment -> createMineFragment(cc);
        }
        return false
    }

    private fun createMineFragment(cc: CC?) {
        CC.sendCCResult(cc?.callId, CCResult.successWithNoKey(MineFragment.newInstance()))
    }
}