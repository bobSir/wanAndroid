package com.bob.wanandroid.test

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * created by cly on 2022/6/10
 */

fun String.replaceLineBlanks(): String {
    if (isNullOrEmpty()) return ""
    val pattern: Pattern = Pattern.compile("\\s*|\r|\n")
    val matcher: Matcher = pattern.matcher(this)
    return matcher.replaceAll("")
}