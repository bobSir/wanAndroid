package com.bob.wanandroid

import android.app.Application
import com.bob.common.util.ContextUtil
import com.bob.lutil.log.BobLog
import com.didichuxing.doraemonkit.DoKit
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk

/**
 * created by cly on 2022/1/25
 */
class WanApplication : Application() {
    companion object {
        lateinit var INSTANCE: WanApplication
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        BobLog.init()
        ContextUtil.init(this)
        initX5()
        initDokit()
    }

    private fun initDokit() {
        DoKit.Builder(this)
            .productId("需要使用平台功能的话，需要到dokit.cn平台申请id")
            .build()
    }

    private fun initX5() {
        QbSdk.initX5Environment(applicationContext, object : QbSdk.PreInitCallback {
            override fun onViewInitFinished(arg0: Boolean) {
            }

            override fun onCoreInitFinished() {
            }
        })
        val map = HashMap<String, Any>()
        map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
        map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
        QbSdk.initTbsSettings(map)
    }
}