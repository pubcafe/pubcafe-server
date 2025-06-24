package com.pubcafe.api.common.model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ResponseForm<T>(
    val code: String,
    val message: String,
    val data: T? = null
) {
    companion object {
        /**
         * 성공
         */
        fun success(code: ResponseCode): ResponseForm<Nothing?> {
            return ResponseForm(
                code = code.name,
                message = code.message
            )
        }

        fun <T> success(code: ResponseCode, data: T): ResponseForm<T> {
            return ResponseForm(
                code = code.name,
                message = code.message,
                data = data
            )
        }

        /**
         * 실패
         */
        fun fail(code: ResponseCode): ResponseForm<Nothing?> {
            return ResponseForm(
                code = code.name,
                message = code.message
            )
        }
    }
}
