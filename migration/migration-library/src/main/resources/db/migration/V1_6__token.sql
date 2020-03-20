create sequence abstract_token_seq start 1 increment 1;
create sequence auth_token_seq start 1 increment 1;

create table auth_token (
    id int8 not null,
    created timestamp not null,
    removed timestamp,
    updated timestamp,
    uuid varchar(255) not null,
    expiration timestamp,
    token varchar(255) not null,
    user_id int8 not null,
    primary key (id)
);

create table token (
    type varchar(31) not null,
    id int8 not null,
    created timestamp not null,
    removed timestamp, updated timestamp,
    uuid varchar(255) not null,
    expiration timestamp,
    token varchar(255) not null,
    primary key (id)
);

alter table auth_token add constraint uk_auth_token_uuid unique (uuid);
alter table auth_token add constraint uk_auth_token_token unique (token);

alter table token add constraint uk_token_uuid unique (uuid);
alter table token add constraint uk_token_token unique (token);

alter table auth_token add constraint fk_auth_token_user_id foreign key (user_id) references user_;
