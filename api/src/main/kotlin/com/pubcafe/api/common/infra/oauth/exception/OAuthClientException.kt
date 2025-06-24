package com.pubcafe.api.common.infra.oauth.exception

import com.pubcafe.api.common.exception.BusinessException
import com.pubcafe.api.common.model.ResponseCode

class OAuthClientException(code: ResponseCode, message: String): BusinessException(code, message)