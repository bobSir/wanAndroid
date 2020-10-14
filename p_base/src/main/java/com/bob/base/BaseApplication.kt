package com.bob.base

import android.app.Activity
import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

/**
 * created by cly on 2020/10/14
 */
open class BaseApplication : Application(), ViewModelStoreOwner {

    private var mAppViewModelStore: ViewModelStore? = null
    private var mFactory: ViewModelProvider.Factory? = null

    override fun onCreate() {
        super.onCreate()
        mAppViewModelStore = ViewModelStore()
    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore!!
    }

    fun getAppViewModelProvider(activity: Activity): ViewModelProvider? {
        return ViewModelProvider(
            activity.applicationContext as BaseApplication,
            (activity.applicationContext as BaseApplication).getAppFactory(activity)
        )
    }

    fun getAppFactory(activity: Activity): ViewModelProvider.Factory {
        val application = checkApplication(activity)
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application!!)
        }
        return mFactory!!
    }

    private fun checkApplication(activity: Activity): Application? {
        return activity.application ?: throw IllegalStateException(
            "Your activity/fragment is not yet attached to "
                    + "Application. You can't request ViewModel before onCreate call."
        )
    }
}