create extension if not exists "uuid-ossp";

create table template_email (
    id BIGINT not null,
    created timestamp not null,
    removed timestamp,
    updated timestamp,
    uuid varchar(255) not null,
    template_name varchar(255) not null,
    type varchar(255) not null,
    primary key (id)
);

alter table template_email add constraint UK_template_email_type_template_name unique (type, template_name);
alter table template_email add constraint UK_template_email_uuid unique (uuid);

insert into template_email (id, created, removed, updated, uuid, template_name, type)
        VALUES (nextval('sequence'), now(), null, now(), uuid_generate_v4(), 'template1', 'FORGOT_PASSWORD');