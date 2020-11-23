package com.bob.wanandroid.ui

import android.os.Bundle
import com.bob.base.ui.base.BaseActivity
import com.bob.wanandroid.R
import com.kunminx.architecture.ui.page.DataBindingConfig

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        TODO()
    }

    override fun initViewModel() {
        TODO("Not yet implemented")
    }
}