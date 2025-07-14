package com.pubcafe.api.common.exception

import com.pubcafe.api.common.model.ResponseCode
import com.pubcafe.api.common.model.ResponseForm
import com.pubcafe.api.common.util.logger
import org.springframework.http.ResponseEntity
import org.springframework.security.authorization.AuthorizationDeniedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

//TODO: 왜 에러 응답 안잡히는지 알아보기 -> CustomAccessDeniedHandler 구현하기
@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = logger()

    @ExceptionHandler(AuthorizationDeniedException::class)
    fun handleAuthorizationDeniedException(e: AuthorizationDeniedException): ResponseEntity<ResponseForm<Unit>> {
        log.warn("Security exception: {} {}", e.javaClass.simpleName, e.message, e)

        val code = ResponseCode.FORBIDDEN_ERROR;
        val response = ResponseForm.fail(
            code = code,
            message = formatMessage(code.message, e.message)
        )

        return ResponseEntity.status(code.httpStatus).body(response)
    }

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(e: BusinessException): ResponseEntity<ResponseForm<Unit>> {
        log.warn("Business exception: {} {}", e.javaClass.simpleName, e.getFullMessage(), e)

        val response = ResponseForm.fail(
            code = e.code,
            message = e.getFullMessage()
        )

        return ResponseEntity.status(e.code.httpStatus).body(response)
    }

    @ExceptionHandler(Exception::class)
    fun handleUnknownException(e: Exception): ResponseEntity<ResponseForm<Unit>> {
        log.error("Unhandled exception occurred: {} {}", e.javaClass.simpleName, e.message, e)

        // TODO: 프로덕트 환경에서 에러 메시지 수정
        val code: ResponseCode = ResponseCode.INTERNAL_SERVER_ERROR
        val response = ResponseForm.fail(
            code = code,
            message = this.formatMessage(code.message, e.message)
        )

        return ResponseEntity.status(code.httpStatus).body(response)
    }

    private fun formatMessage(message: String, detail: String?): String {
        return "$message ${detail ?: ""}".trim()
    }
}