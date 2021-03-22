create table if not exists token_personal_access
(
    id          int8         not null,
    created     timestamp    not null,
    removed     timestamp,
    updated     timestamp,
    user_id     bigint       not null
        constraint fk_token_personal_access_user_id
            references user_,
    entity_type varchar(100) not null,
    entity_id   varchar(255) not null
)