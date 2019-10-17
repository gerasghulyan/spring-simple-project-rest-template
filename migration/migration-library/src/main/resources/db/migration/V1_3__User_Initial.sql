create table user_
(
    id        int8         not null,
    created   timestamp    not null,
    removed   timestamp,
    updated   timestamp,
    uuid      varchar(255) not null,
    email     varchar(255) not null,
    full_name varchar(255) not null,
    password  varchar(255) not null,
    primary key (id)
);

alter table user_
    add constraint UK_user_uuid unique (uuid);

alter table user_
    add constraint UK_user_email unique (email);