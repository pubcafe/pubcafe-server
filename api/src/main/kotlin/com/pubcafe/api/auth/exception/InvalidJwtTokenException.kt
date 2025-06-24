package com.pubcafe.api.auth.exception

import com.pubcafe.api.common.exception.BusinessException
import com.pubcafe.api.common.model.ResponseCode

class InvalidJwtTokenException: BusinessException(ResponseCode.INVALID_TOKEN_ERROR)