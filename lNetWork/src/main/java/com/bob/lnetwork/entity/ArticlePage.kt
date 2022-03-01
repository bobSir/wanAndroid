package com.bob.lnetwork.entity

/**
 * created by cly on 2022/1/26
 */
data class ArticlePage(
    val curPage: Int,
    val datas: List<Article>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)