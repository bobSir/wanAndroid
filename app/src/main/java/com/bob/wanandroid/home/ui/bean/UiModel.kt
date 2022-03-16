package com.bob.wanandroid.home.ui.bean

import com.bob.lnetwork.entity.Article

/**
 * created by cly on 2022/1/27
 */
sealed class UiModel {
    data class ArticleItem(val article: Article) : UiModel()
    data class SeparatorItem(val description: String) : UiModel()
}
