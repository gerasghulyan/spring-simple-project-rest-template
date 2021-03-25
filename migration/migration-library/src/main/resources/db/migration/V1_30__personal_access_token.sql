create table if not exists token_personal_access
(
    id      int8      not null,
    user_id bigint    not null
        constraint fk_token_personal_access_user_id
            references user_
)