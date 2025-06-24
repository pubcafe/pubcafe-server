package com.pubcafe.api.auth.exception

import com.pubcafe.api.common.exception.BusinessException
import com.pubcafe.api.common.model.ResponseCode

class ExpiredJwtTokenException: BusinessException(ResponseCode.EXPIRED_TOKEN_ERROR) {
}