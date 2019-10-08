CREATE SEQUENCE sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

create table user_ (id bigint not null,
    created timestamp not null,
    updated timestamp,
    uuid varchar(255) not null,
    first_name varchar(255),
    second_name varchar(255),
    primary key (id)
);

alter table user_ add constraint UK_user_uuid unique (uuid);