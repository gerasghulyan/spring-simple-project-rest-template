create table if not exists organization
(
    id                bigint       not null
        constraint organization_pkey
            primary key,
    created           timestamp    not null,
    removed           timestamp,
    updated           timestamp,
    uuid              varchar(255) not null
        constraint uk_organization_uuid
            unique,
    organization_name varchar(255) not null,
    organization_status varchar(255) NOT NULL
);
create sequence organization_seq start 1 increment 1;