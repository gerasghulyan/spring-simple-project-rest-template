create sequence anonymous_user_seq start 1 increment 1;

create table anonymous_user
(

    external_uuid         varchar(255) not null,
    user_id               bigint       not null,
    anonymous_user_source varchar(255) not null default 'OTTO',
    created               timestamp    not null,
    removed               timestamp,
    updated               timestamp
);

alter table anonymous_user
    add constraint uk_anonymous_user_external_uuid unique (external_uuid);