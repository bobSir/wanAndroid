package com.bob.base

import androidx.lifecycle.ViewModelStore
import com.kunminx.architecture.BaseApplication

/**
 * created by cly on 2020/10/14
 */
open class WanBaseApplication : BaseApplication() {

    val mAppViewModelStore: ViewModelStore? = null

    override fun onCreate() {
        super.onCreate()
    }

    override fun getViewModelStore(): ViewModelStore {
        return super.getViewModelStore()
    }
}