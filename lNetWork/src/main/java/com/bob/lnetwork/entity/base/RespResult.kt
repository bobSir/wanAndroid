package com.bob.lnetwork.entity.base

/**
 * created by cly on 2022/1/25
 */
sealed class RespResult<out T> {
    companion object {
        fun <T> success(result: T): RespResult<T> = Success(result)
        fun <T> failure(msg: String): RespResult<T> = Failure(msg)
        fun <T> netError(msg: String): RespResult<T> = NetError(msg)
    }

    data class Success<out T>(val data: T) : RespResult<T>()
    data class Failure(val msg: String) : RespResult<Nothing>()
    data class NetError(val msg: String) : RespResult<Nothing>()
}