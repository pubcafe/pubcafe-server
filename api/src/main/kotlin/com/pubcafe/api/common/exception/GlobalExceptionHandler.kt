package com.pubcafe.api.common.exception

import com.pubcafe.api.common.model.ResponseCode
import com.pubcafe.api.common.model.ResponseForm
import com.pubcafe.api.common.util.logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = logger()

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(ex: BusinessException): ResponseEntity<ResponseForm<Unit>> {
        log.warn("Business exception: {} {}", ex.javaClass.simpleName, ex.getFullMessage())

        val response = ResponseForm<Unit>(
            code = ex.code.name,
            message = ex.getFullMessage()
        )

        return ResponseEntity.status(ex.code.httpStatus).body(response)
    }

    @ExceptionHandler(Exception::class)
    fun handleUnknownException(ex: Exception): ResponseEntity<ResponseForm<Unit>> {
        log.error("Unhandled exception occurred: {} {}", ex.javaClass.simpleName, ex.message)
        val code: ResponseCode = ResponseCode.INTERNAL_SERVER_ERROR

        // TODO: 프로덕트 환경에서 에러 메시지 수정
        val response = ResponseForm<Unit>(
            code = code.name,
            message = "${code.message} ${ex.message ?: ""}".trim()
        )

        return ResponseEntity.status(code.httpStatus).body(response)
    }
}