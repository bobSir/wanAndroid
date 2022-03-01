package com.bob.lnetwork.api

import com.bob.lnetwork.api.base.ApiBase
import com.bob.lnetwork.api.service.HomeService
import com.bob.lnetwork.entity.ArticlePage
import com.bob.lnetwork.entity.base.BaseResponse


/**
 * created by cly on 2022/1/26
 */
class HomeApi : ApiBase() {

    private val homeService: HomeService = retrofit.create(HomeService::class.java)

    suspend fun queryHotArticle(pageIndex: Int): BaseResponse<ArticlePage> {
        val map = getDefaultMap().toMutableMap()
        map["page_size"] = 30
        return homeService.queryHotArticle(pageIndex, map)
    }

    suspend fun queryQAList(pageIndex: Int): BaseResponse<ArticlePage> {
        val map = getDefaultMap().toMutableMap()
        map["page_size"] = 30
        return homeService.queryQAList(pageIndex, map)
    }

    override fun getDefaultMap(): Map<String, Any> {
        return HashMap()
    }
}