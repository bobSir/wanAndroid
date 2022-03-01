package com.bob.wanandroid.home.vm

import com.bob.common.listloadmore.vm.PagingViewModel
import com.bob.lnetwork.entity.Article
import com.bob.lnetwork.entity.base.RespResult
import com.bob.wanandroid.home.repository.QALocalDataSource
import com.bob.wanandroid.home.repository.QARemoteDataSource
import com.bob.wanandroid.home.repository.QARepository

/**
 * created by cly on 2022/2/7
 */
class QAViewModel : PagingViewModel<Article, Int>() {
    private val qaRepository: QARepository = QARepository(QARemoteDataSource(), QALocalDataSource())

    init {
        refresh()
    }

    override suspend fun doRefresh() {
        val cacheResult = qaRepository.queryCache()
        if (cacheResult?.isNotEmpty() == true) {
            refreshSuccess(cacheResult, 1, false)
        }
        when (val result = qaRepository.query(0)) {
            is RespResult.Success -> refreshSuccess(result.data.datas, 1, false)
            is RespResult.Failure -> refreshFailure()
            is RespResult.NetError -> refreshFailure()
        }
    }

    override suspend fun doLoadMore(version: Int, pagingParams: Int) {
        when (val result = qaRepository.query(pagingParams)) {
            is RespResult.Success -> loadMoreSuccess(version, result.data.datas, pagingParams + 1, false)
            is RespResult.Failure -> loadMoreFailure(version)
            is RespResult.NetError -> loadMoreFailure(version)
        }
    }
}