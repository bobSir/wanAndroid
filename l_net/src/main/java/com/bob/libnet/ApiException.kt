package com.bob.libnet

/**
 * created by cly on 2020/12/16
 */
class ApiException(val code: Int, override val message: String) : RuntimeException()