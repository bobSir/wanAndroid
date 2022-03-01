package com.bob.wanandroid.home.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bob.base.repository.BaseRepositoryBoth
import com.bob.base.repository.ILocalDataSource
import com.bob.base.repository.IRemoteDataSource
import com.bob.lnetwork.api.HomeApi
import com.bob.lnetwork.entity.Article
import com.bob.lnetwork.entity.ArticlePage
import com.bob.lnetwork.entity.base.BaseResponse

/**
 * created by cly on 2022/1/26
 */
class HomeRepository constructor(
    remoteDataSource: HomeRemoteDataSource,
    localDataSource: HomeLocalDataSource
) : BaseRepositoryBoth<HomeRemoteDataSource, HomeLocalDataSource>(
    remoteDataSource,
    localDataSource
) {
    fun fetchHotArticle(): Pager<Int, Article> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { HomeRemotePagingSource(remoteDataSource, localDataSource) }
        )
    }
}

class HomeRemotePagingSource(
    private val remoteDataSource: HomeRemoteDataSource,
    private val localDataSource: HomeLocalDataSource
) : PagingSource<Int, Article>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: 0
        return try {
            val response = remoteDataSource.queryHotArticle(position)
            val datas = response.data.datas
            val nextKey = if (datas.isEmpty()) {
                null
            } else {
                position + 1
            }
            LoadResult.Page(
                data = datas,
                prevKey = if (position == 0) null else position - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}

class HomeRemoteDataSource : IRemoteDataSource {
    private val homeApi: HomeApi = HomeApi()

    suspend fun queryHotArticle(pageIndex: Int): BaseResponse<ArticlePage> {
        return homeApi.queryHotArticle(pageIndex)
    }
}

class HomeLocalDataSource : ILocalDataSource {
    fun fetchHotArticle(): List<Article>? {
        return null
    }

    fun putCacheDatas(datas: List<Article>) {
    }
}