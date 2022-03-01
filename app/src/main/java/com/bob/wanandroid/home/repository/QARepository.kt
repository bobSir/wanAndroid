package com.bob.wanandroid.home.repository

import com.bob.base.repository.BaseRepositoryBoth
import com.bob.base.repository.ILocalDataSource
import com.bob.base.repository.IRemoteDataSource
import com.bob.common.cache.WanDiskCache
import com.bob.lnetwork.api.HomeApi
import com.bob.lnetwork.entity.Article
import com.bob.lnetwork.entity.ArticlePage
import com.bob.lnetwork.entity.base.RespResult
import com.bob.lnetwork.ext.processApiResponse

/**
 * created by cly on 2022/2/7
 */
class QARepository constructor(
    remoteDataSource: QARemoteDataSource,
    localDataSource: QALocalDataSource
) : BaseRepositoryBoth<QARemoteDataSource, QALocalDataSource>(
    remoteDataSource,
    localDataSource
) {
    fun queryCache(): List<Article>? {
        return localDataSource.fetchQAList()
    }

    suspend fun query(pageIndex: Int): RespResult<ArticlePage> {
        val result = remoteDataSource.queryQAList(pageIndex)
        if (pageIndex == 1 && result is RespResult.Success) {
            localDataSource.insertQAList(result.data.datas)
        }
        return result
    }
}

class QARemoteDataSource : IRemoteDataSource {
    private val homeApi: HomeApi = HomeApi()

    suspend fun queryQAList(pageIndex: Int): RespResult<ArticlePage> {
        return processApiResponse { homeApi.queryQAList(pageIndex) }
    }
}

class QALocalDataSource : ILocalDataSource {
    private val KEY = "qa_list_datas"

    fun fetchQAList(): List<Article>? {
        return WanDiskCache.getInstance().get<List<Article>>(KEY)
    }

    fun insertQAList(datas: List<Article>) {
        WanDiskCache.getInstance().put(KEY, datas)
    }
}