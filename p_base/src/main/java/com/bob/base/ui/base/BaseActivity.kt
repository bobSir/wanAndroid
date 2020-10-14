package com.bob.base.ui.base

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bob.base.BaseApplication
import com.bob.base.bridge.callback.SharedViewModel

/**
 * created by cly on 2020/10/13
 */
open class BaseActivity : AppCompatActivity() {

    protected var mSharedViewModel: SharedViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSharedViewModel = getAppViewModelProvider()!![SharedViewModel::class.java]
    }

    override fun getResources(): Resources {
        return super.getResources()
    }

    protected open fun getAppViewModelProvider(): ViewModelProvider? {
        return (applicationContext as BaseApplication).getAppViewModelProvider(this)
    }

    protected open fun getActivityViewModelProvider(activity: AppCompatActivity): ViewModelProvider? {
        return ViewModelProvider(activity, activity.defaultViewModelProviderFactory)
    }
}