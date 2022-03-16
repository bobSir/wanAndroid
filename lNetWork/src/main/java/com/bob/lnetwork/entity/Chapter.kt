package com.bob.lnetwork.entity

import java.io.Serializable

/**
 * created by cly on 2022/3/16
 */
data class Chapter(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
) : Serializable