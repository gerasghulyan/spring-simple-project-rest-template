create sequence external_user_seq start 1 increment 1;

create table external_user
(
    id                    int8         not null
        constraint external_user_pkey
            primary key,
    uuid                  varchar(255) not null
        constraint uk_external_user_uuid
            unique,
    user_id               bigint       not null
        constraint external_user_user_id_fk
            references user_,
    external_user_source varchar(255) not null default 'OTTO',
    created               timestamp    not null,
    removed               timestamp,
    updated               timestamp
);

alter table user_
    alter column email drop not null;
alter table user_
    alter column password drop not null;