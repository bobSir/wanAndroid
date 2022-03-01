package com.bob.lnetwork.ext

import com.bob.lnetwork.entity.base.BaseResponse
import com.bob.lnetwork.entity.base.RespResult
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

/**
 * created by cly on 2022/1/25
 */
inline fun <T> processApiResponse(request: () -> BaseResponse<T>): RespResult<T> {
    return try {
        val response = request()
        if (response.errorCode == 0) {
            RespResult.success(response.data!!)
        } else {
            RespResult.failure(response.errorMsg)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        when (e) {
            is HttpException -> RespResult.netError("网络错误")
            is UnknownHostException -> RespResult.netError("网络错误")
            is ConnectException -> RespResult.netError("连接失败")
            is SSLHandshakeException -> RespResult.netError("证书验证失败")
            is SocketTimeoutException -> RespResult.netError("连接超时")
            is JsonSyntaxException -> RespResult.netError("JSON异常")
            else -> RespResult.netError("网络异常")
        }
    }
}