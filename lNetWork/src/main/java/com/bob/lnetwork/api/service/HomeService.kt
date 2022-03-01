package com.bob.lnetwork.api.service

import com.bob.lnetwork.entity.ArticlePage
import com.bob.lnetwork.entity.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * created by cly on 2022/1/26
 */
interface HomeService {
    @GET("/article/list/{pageIndex}/json")
    suspend fun queryHotArticle(
        @Path("pageIndex") pageIndex: Int,
        @QueryMap params: Map<String, @JvmSuppressWildcards Any>
    ): BaseResponse<ArticlePage>

    @GET("/wenda/list/{pageIndex}/json")
    suspend fun queryQAList(
        @Path("pageIndex") pageIndex: Int,
        @QueryMap params: Map<String, @JvmSuppressWildcards Any>
    ): BaseResponse<ArticlePage>
}