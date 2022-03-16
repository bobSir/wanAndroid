package com.bob.wanandroid.topic.repository

import com.bob.base.repository.BaseRepositoryBoth
import com.bob.base.repository.ILocalDataSource
import com.bob.base.repository.IRemoteDataSource
import com.bob.lnetwork.api.TopicApi
import com.bob.lnetwork.entity.ArticlePage
import com.bob.lnetwork.entity.Chapter
import com.bob.lnetwork.entity.base.RespResult
import com.bob.lnetwork.ext.processApiResponse

/**
 * created by cly on 2022/3/16
 */
class TopicRepository constructor(
    remoteRepository: TopicRemoteRepository,
    localRepository: TopicLocalRepository
) : BaseRepositoryBoth<TopicRemoteRepository, TopicLocalRepository>(
    remoteRepository,
    localRepository
) {
    suspend fun fetchChapters(): RespResult<List<Chapter>> {
        return remoteDataSource.fetchChapters()
    }

    suspend fun fetchChapterArticles(id: Int, pageIndex: Int): RespResult<ArticlePage> {
        return remoteDataSource.fetchChapterArticles(id, pageIndex)
    }

}

class TopicRemoteRepository : IRemoteDataSource {

    private val topicApi: TopicApi = TopicApi()

    suspend fun fetchChapters(): RespResult<List<Chapter>> {
        return processApiResponse { topicApi.fetchTopic() }
    }

    suspend fun fetchChapterArticles(id: Int, pageIndex: Int): RespResult<ArticlePage> {
        return processApiResponse { topicApi.fetchTopicArticle(id, pageIndex) }
    }
}

class TopicLocalRepository : ILocalDataSource {

}