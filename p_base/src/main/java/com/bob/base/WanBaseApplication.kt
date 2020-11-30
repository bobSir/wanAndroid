package com.bob.base

import android.app.Application
import androidx.lifecycle.ViewModelStore
import dagger.hilt.android.HiltAndroidApp

/**
 * created by cly on 2020/10/14
 */
@HiltAndroidApp
open class WanBaseApplication : Application() {

    val mAppViewModelStore: ViewModelStore? = null

    override fun onCreate() {
        super.onCreate()
    }
}