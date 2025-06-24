package com.pubcafe.api.auth.exception

import com.pubcafe.api.common.exception.BusinessException
import com.pubcafe.api.common.model.ResponseCode

class UnsupportedOAuthProviderException: BusinessException(ResponseCode.BAD_REQUEST_ERROR, "지원하지 않는 OAuth 공급자 입니다.")