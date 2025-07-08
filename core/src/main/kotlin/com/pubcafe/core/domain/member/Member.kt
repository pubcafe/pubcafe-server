package com.pubcafe.core.domain.member

import com.pubcafe.core.domain.common.BaseTimeEntity
import jakarta.persistence.*
import org.hibernate.annotations.Comment

@Entity
@Table(name = "member")
class Member(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var profile: MemberProfile? = null,

    @Comment("이메일")
    @Column(name = "email", nullable = false, unique = true)
    val email: String,

    @Comment("권한")
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    val role: MemberRole

) : BaseTimeEntity() {

    companion object {
        fun create(email: String): Member {
            return Member(
                email = email,
                role = MemberRole.NEW_MEMBER
            )
        }
    }
}
