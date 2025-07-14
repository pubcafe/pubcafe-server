package com.pubcafe.api.member.service

import com.pubcafe.api.common.exception.BusinessException
import com.pubcafe.api.common.model.ResponseCode
import com.pubcafe.core.domain.member.Member
import com.pubcafe.core.domain.member.MemberRole
import com.pubcafe.core.repository.MemberProfileRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class MemberValidationService(
    private val memberProfileRepository: MemberProfileRepository
) {

    fun validateNewMember(member: Member) {
        if (member.role != MemberRole.NEW_MEMBER || memberProfileRepository.isExistByMember(member)) {
            throw BusinessException(ResponseCode.CONFLICT_ERROR, "이미 가입된 회원입니다.")
        }
    }

    fun validateUniqueAlias(alias: String) {
        if (memberProfileRepository.isExistAlias(alias)) {
            throw BusinessException(ResponseCode.NOT_FOUND_ERROR, "이미 존재하는 고유 이름입니다.")
        }
    }
}