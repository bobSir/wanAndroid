package com.bob.libnet

import retrofit2.Call
import retrofit2.http.GET

/**
 * created by cly on 2020/12/14
 */
interface ApiService {

    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }

    @GET("/wxarticle/chapters/json")
    fun getTest(): Call<String>
}