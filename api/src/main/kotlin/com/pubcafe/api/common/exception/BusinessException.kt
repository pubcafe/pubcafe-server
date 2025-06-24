package com.pubcafe.api.common.exception

import com.pubcafe.api.common.model.ResponseCode

open class BusinessException(
    val code: ResponseCode,
    message: String? = null,
    cause: Throwable? = null
) : RuntimeException(message, cause) {

    fun getFullMessage(): String {
        return "${code.message} ${message ?: ""}".trim()
    }
}