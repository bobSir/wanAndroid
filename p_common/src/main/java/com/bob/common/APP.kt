package com.bob.common

import com.bob.base.BuildConfig
import com.bob.base.WanBaseApplication
import com.bob.libutil.util.WanLog

/**
 * created by cly on 2020/10/14
 */
class APP : WanBaseApplication() {

    override fun onCreate() {
        super.onCreate()
        WanLog.init(BuildConfig.DEBUG)
    }
}