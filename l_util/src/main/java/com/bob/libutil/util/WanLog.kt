package com.bob.libutil.util

import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog

/**
 * created by cly on 2020/12/8
 */
fun logD(msg: Any) {
    XLog.d(msg)
}

fun logD(tag: String, msg: Any) {
    XLog.tag(tag).d(msg)
}

fun logI(msg: Any) {
    XLog.i(msg)
}

fun logI(tag: String, msg: Any) {
    XLog.tag(tag).i(msg)
}

fun logE(msg: Any) {
    XLog.e(msg)
}

fun logE(tag: String, msg: Any) {
    XLog.tag(tag).e(msg)
}

class WanLog {
    companion object {
        fun init(debug: Boolean) {
            val configuration = LogConfiguration.Builder()
                .logLevel(
                    if (debug) LogLevel.ALL
                    else LogLevel.NONE
                )
                .tag("wanAndroid")
                .enableThreadInfo()
                .enableStackTrace(2)
                .enableBorder()
                .build()
            XLog.init(configuration)
        }
    }
}