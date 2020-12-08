package com.bob.wanandroid

import com.bob.base.WanBaseApplication
import com.bob.common.util.WanLog

/**
 * created by cly on 2020/10/14
 */
class APP : WanBaseApplication() {

    override fun onCreate() {
        super.onCreate()
        WanLog.init(BuildConfig.DEBUG)
    }
}