package com.bob.lnetwork.entity.base

import com.google.gson.annotations.SerializedName

/**
 * created by cly on 2022/1/25
 */
data class BaseResponse<T>(
    @SerializedName("data")
    val data: T,
    @SerializedName("errorCode")
    val errorCode: Int,
    @SerializedName("errorMsg")
    val errorMsg: String
)