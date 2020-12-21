package com.bob.libnet.business

import com.bob.libnet.RetrofitClient

/**
 * created by cly on 2020/12/16
 */
class TestRepository {
    fun test1() = RetrofitClient.apiService.getTest()
}