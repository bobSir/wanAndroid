package com.bob.base.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * created by cly on 2022/1/25
 */
abstract class BaseActivity : AppCompatActivity() {

    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        initView()
        subscribeUi()
    }

    open fun initView() {}

    abstract fun subscribeUi()
}