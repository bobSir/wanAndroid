package com.bob.wanandroid.topic.ui.adapter

import com.bob.lnetwork.entity.Article
import com.bob.wanandroid.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * created by cly on 2022/3/16
 */
class ChapterArticleAdapter(
    articles: MutableList<Article>
) : BaseQuickAdapter<Article, BaseViewHolder>(
    R.layout.item_public_article,
    articles
), LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: Article) {
        holder.setText(R.id.tv_title, item.title)
            .setText(R.id.tv_time, item.publishTime.toString())
    }
}