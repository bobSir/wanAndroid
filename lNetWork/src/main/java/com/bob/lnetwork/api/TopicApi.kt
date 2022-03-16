package com.bob.lnetwork.api

import com.bob.lnetwork.api.base.ApiBase
import com.bob.lnetwork.api.service.TopicService
import com.bob.lnetwork.entity.ArticlePage
import com.bob.lnetwork.entity.Chapter
import com.bob.lnetwork.entity.base.BaseResponse

/**
 * created by cly on 2022/3/16
 */
class TopicApi : ApiBase() {

    private val topicService: TopicService = retrofit.create(TopicService::class.java)

    suspend fun fetchTopic(): BaseResponse<List<Chapter>> {
        return topicService.fetchChapters()
    }

    suspend fun fetchTopicArticle(id: Int, pageIndex: Int): BaseResponse<ArticlePage> {
        val map = getDefaultMap().toMutableMap()
        map["page_size"] = 30
        return topicService.fetchChapterArticle(id, pageIndex, map)
    }

    override fun getDefaultMap(): Map<String, Any> {
        return HashMap()
    }
}