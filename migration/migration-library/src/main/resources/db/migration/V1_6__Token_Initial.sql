create table token
(
    type    varchar(31)  not null,
    id      int8         not null,
    created timestamp    not null,
    removed timestamp,
    updated timestamp,
    uuid    varchar(255) not null,
    token   varchar(255) not null,
    primary key (id)
);

alter table token
    add constraint UK_token_uuid unique (uuid);

alter table token
    add constraint UK_token unique (token);