package com.pubcafe.core.domain.member

import com.pubcafe.core.converter.LanguageCodeListConverter
import com.pubcafe.core.converter.LinkListConverter
import com.pubcafe.core.domain.common.BaseTimeEntity
import com.pubcafe.core.domain.common.CountryCode
import com.pubcafe.core.domain.common.LanguageCode
import com.pubcafe.core.domain.member.dto.ProfileCreateForm
import jakarta.persistence.*
import org.hibernate.annotations.Comment

@Entity
@Table(name = "member_profile")
class MemberProfile(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, unique = true)
    val member: Member,

    @Comment("고유 아이디")
    @Column(nullable = false, unique = true)
    var alias: String,

    @Comment("공개 이름")
    @Column(name = "display_name", length = 32, nullable = false)
    var displayName: String,

    @Comment("소개글")
    @Column(name = "introduction", length = 256)
    var introduction: String?,

    @Comment("국적")
    @Enumerated(EnumType.STRING)
    @Column(length = 2, nullable = false)
    var country: CountryCode,

    @Comment("연락처")
    @Column(name = "contact", nullable = false)
    var contact: String?,

    @Comment("사용 가능 언어")
    @Enumerated(EnumType.STRING)
    @Convert(converter = LanguageCodeListConverter::class)
    @Column(name = "languages")
    var languages: List<LanguageCode> = emptyList(),

    @Comment("관련 링크")
    @Convert(converter = LinkListConverter::class)
    @Column(name = "links")
    var links: List<String> = emptyList(),

    @Comment("프로필 이미지 URL")
    @Column(name = "profile_image_url", nullable = false)
    var profileImage: String?,

    @Comment("배너 이미지 URL")
    @Column(name = "banner_image_url", nullable = false)
    var bannerImage: String?

) : BaseTimeEntity() {

    companion object {
        fun createFor(member: Member, form: ProfileCreateForm): MemberProfile {
            return MemberProfile(
                member = member,
                alias = form.alias,
                displayName = form.displayName,
                introduction = form.introduction,
                country = form.country,
                contact = form.contact,
                languages = form.languages,
                links = form.links,
                profileImage = form.profileImage,
                bannerImage = form.bannerImage
            )
        }
    }
}
