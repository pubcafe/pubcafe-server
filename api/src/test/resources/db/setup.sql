/*
    MEMBER
 */

-- NEW_MEMBER
INSERT INTO member (id, email, role, created_at, updated_at)
VALUES (100, 'new_member@example.com', 'NEW_MEMBER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- MEMBER
INSERT INTO member (id, email, role, created_at, updated_at)
VALUES (101, 'member1@example.com', 'MEMBER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

/*
    MEMBER_PROFILE
 */
INSERT INTO member_profile (member_id, alias, display_name, introduction, gender, country,
                            contact, languages, links, profile_image_url, banner_image_url,
                            created_at, updated_at)
VALUES (101,
        'unique_alias_1',
        'pubcafe',
        '안녕하세요. pubcafe 입니다.',
        'MALE',
        'KR',
        '010-1234-5678',
        'KO,EN',
        'https://github.com/pubcafe,https://pubcafe.dev',
        'https://cdn.example.com/images/profile.jpg',
        'https://cdn.example.com/images/banner.jpg',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP);
