package com.bob.lnetwork.api.service

import com.bob.lnetwork.entity.ArticlePage
import com.bob.lnetwork.entity.Chapter
import com.bob.lnetwork.entity.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * created by cly on 2022/3/16
 */
interface TopicService {
    @GET("/wxarticle/chapters/json")
    suspend fun fetchChapters(): BaseResponse<List<Chapter>>

    @GET("wxarticle/list/{id}/{pageIndex}/json")
    suspend fun fetchChapterArticle(
        @Path("id") id: Int,
        @Path("pageIndex") pageIndex: Int,
        @QueryMap params: Map<String, @JvmSuppressWildcards Any>
    ): BaseResponse<ArticlePage>
}