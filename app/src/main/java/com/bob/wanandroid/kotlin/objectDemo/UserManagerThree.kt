package com.bob.wanandroid.kotlin.objectDemo

/**
 * created by cly on 2022/9/6
 */
class UserManagerThree private constructor() {

    companion object {
        @JvmStatic
        val instance by lazy { UserManagerThree() }
    }
}