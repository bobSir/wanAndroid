package com.bob.wanandroid.home.vm

import androidx.lifecycle.viewModelScope
import com.bob.common.listloadmore.vm.PagingViewModel
import com.bob.lnetwork.entity.Article
import com.bob.lnetwork.entity.base.RespResult
import com.bob.lutil.log.BobLog
import com.bob.wanandroid.home.repository.QALocalDataSource
import com.bob.wanandroid.home.repository.QARemoteDataSource
import com.bob.wanandroid.home.repository.QARepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * net cache
 * created by cly on 2022/2/7
 */
class QAViewModel : PagingViewModel<Article, Int>() {
    private val qaRepository: QARepository = QARepository(QARemoteDataSource(), QALocalDataSource())

    init {
//        refresh()
//        loadCache()
    }

    override fun loadCache() {
        viewModelScope.launch(Dispatchers.IO) {
            BobLog.d("@cly", 0)
            val cacheResult = qaRepository.queryCache()
            BobLog.d("@cly", 2)
            if (cacheResult?.isNotEmpty() == true) {
                BobLog.d("@cly", 3)
                refreshSuccess(cacheResult, 1, false)
            }
        }
    }

    override suspend fun doRefresh() {
        when (val result = qaRepository.query(0)) {
            is RespResult.Success -> refreshSuccess(result.data.datas, 1, false)
            is RespResult.Failure -> refreshFailure()
            is RespResult.NetError -> refreshFailure()
        }
    }

    override suspend fun doLoadMore(version: Int, pagingParams: Int) {
        when (val result = qaRepository.query(pagingParams)) {
            is RespResult.Success -> {
                loadMoreSuccess(
                    version, result.data.datas, pagingParams + 1,
                    result.data.datas.isEmpty()
                )
            }
            is RespResult.Failure -> loadMoreFailure(version)
            is RespResult.NetError -> loadMoreFailure(version)
        }
    }
}