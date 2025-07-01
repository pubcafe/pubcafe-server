package com.pubcafe.core.entity

import com.pubcafe.core.entity.common.BaseTimeEntity
import jakarta.persistence.*

@Entity
@Table(name = "member")
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "name")
    var name: String? = null,

    @Column(name = "nickname")
    var nickname: String? = null,

    @Column(name = "email", nullable = false, unique = true)
    var email: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    var role: MemberRole

) : BaseTimeEntity() {

    companion object {
        fun createNew(email: String): Member {
            return Member(
                email = email,
                role = MemberRole.NEW_MEMBER
            )
        }
    }
}
