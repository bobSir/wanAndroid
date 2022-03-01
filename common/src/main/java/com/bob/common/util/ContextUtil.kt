package com.bob.common.util

import android.annotation.SuppressLint
import android.content.Context

/**
 * created by cly on 2022/1/24
 */
@SuppressLint("StaticFieldLeak")
object ContextUtil {
    private lateinit var INSTANCE: Context

    fun init(context: Context) {
        INSTANCE = context.applicationContext
    }

    fun getContext(): Context {
        return INSTANCE
    }
}
