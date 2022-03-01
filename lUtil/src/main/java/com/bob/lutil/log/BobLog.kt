package com.bob.lutil.log

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

/**
 * created by cly on 2022/1/25
 */
class BobLog {
    companion object {
        fun init() {
            val build = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)
                .methodCount(1)
                .methodOffset(2)
                .build()
            Logger.addLogAdapter(object : AndroidLogAdapter(build) {
                override fun isLoggable(priority: Int, tag: String?): Boolean {
                    return true
                }
            })
        }

        fun d(any: Any?) {
            Logger.d(any)
        }

        fun d(tag: String, any: Any?) {
            Logger.t(tag).d(any)
        }

        fun e(tag: String, any: Any?) {
            Logger.e(tag, any)
        }
    }
}