package com.pubcafe.api.common.model

data class ResponseForm<T>(
    val code: String,
    val message: String,
    val data: T
) {
    companion object {
        /**
         * 성공
         */
        fun success(code: ResponseCode): ResponseForm<Unit> {
            return ResponseForm(
                code = code.name,
                message = code.message,
                data = Unit
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
        fun fail(code: ResponseCode, message: String): ResponseForm<Unit> {
            return ResponseForm(
                code = code.name,
                message = message,
                data = Unit
            )
        }
    }
}
