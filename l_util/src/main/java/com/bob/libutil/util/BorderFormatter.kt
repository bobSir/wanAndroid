package com.bob.libutil.util

import com.elvishew.xlog.formatter.border.DefaultBorderFormatter
import com.elvishew.xlog.internal.SystemCompat


/**
 * created by cly on 2020/12/21
 */
class BorderFormatter : DefaultBorderFormatter() {

    override fun format(segments: Array<out String>?): String {
        var formatted = super.format(segments)
        if (formatted.isNotEmpty()) {
            formatted = " " + SystemCompat.lineSeparator + formatted
        }
        return formatted
    }
}