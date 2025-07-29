package com.pubcafe.api.common.model

import org.springframework.http.HttpStatus

enum class ResponseCode(
    val httpStatus: HttpStatus,
    val message: String
) {
    /*
        성공
    */
    SUCCESS(HttpStatus.OK, "요청 처리를 성공했습니다."),
    GET_SUCCESS(HttpStatus.OK, "조회에 성공했습니다."),
    LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공했습니다."),
    SIGN_UP_SUCCESS(HttpStatus.CREATED, "회원가입에 성공했습니다."),

    /*
        실패
    */

    // 400 BAD REQUEST
    BAD_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "유효하지 않은 요청 입니다."),

    // 401 UNAUTHORIZED
    INVALID_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "만료된 토큰 입니다."),

    // 403 FORBIDDEN
    FORBIDDEN_ERROR(HttpStatus.FORBIDDEN, "접근 권한이 필요합니다."),

    // 404 NOT FOUND
    NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "대상 리소스를 찾을 수 없습니다."),

    // 409 CONFLICT
    CONFLICT_ERROR(HttpStatus.CONFLICT, "대상 리소스의 현재 상태와 충돌하여 요청을 완료할 수 없습니다."),

    // 500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 에러가 발생했습니다."),
    BAD_GATEWAY_ERROR(HttpStatus.BAD_GATEWAY, "외부 요청 서버에 일시적인 문제가 발생했습니다. 잠시 후 다시 시도해주세요.")
}