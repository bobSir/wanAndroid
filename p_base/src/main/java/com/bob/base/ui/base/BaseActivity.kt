package com.bob.base.ui.base

import android.content.res.Resources
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kunminx.architecture.ui.page.DataBindingActivity

/**
 * created by cly on 2020/10/13
 */
abstract class BaseActivity : DataBindingActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun <T : ViewModel?> getActivityViewModel(modelClass: Class<T>): T {
        return super.getActivityViewModel(modelClass)
    }

    override fun getAppViewModelProvider(): ViewModelProvider {
        return super.getAppViewModelProvider()
    }

    override fun getResources(): Resources {
        return super.getResources()
    }


}