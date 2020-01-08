-- start organization
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
    organization_slug varchar(255) not null
        constraint uk_organization_slug_id
            unique
);
create sequence organization_seq start 1 increment 1;
-- end organization

-- start client-organization
create table if not exists client_organization
(
    id                       bigint       not null
        constraint client_organization_pkey
            primary key,
    created                  timestamp    not null,
    removed                  timestamp,
    updated                  timestamp,
    uuid                     varchar(255) not null
        constraint uk_client_organization_uuid
            unique,
    client_organization_name varchar(255) not null,
    client_organization_slug varchar(255) not null,
    organization_id          bigint       not null
        constraint fk_organization_id
            references organization,
    image_id                 varchar(255) not null,
    constraint uk_client_organization_slug_organization_id
        unique (client_organization_slug, organization_id)
);
create sequence client_organization_seq start 1 increment 1;
-- end client-organization

-- start template-email
create table if not exists template_email
(
    id            bigint       not null
        constraint template_email_pkey
            primary key,
    created       timestamp    not null,
    removed       timestamp,
    updated       timestamp,
    uuid          varchar(255) not null
        constraint uk_template_email_uuid
            unique,
    template_name varchar(255),
    type          varchar(255) not null,
    constraint uk_template_email_type_template_name
        unique (type, template_name)
);
create sequence template_email_seq start 1 increment 1;
-- end template-email

-- start user
create table if not exists user_
(
    id        bigint       not null
        constraint user__pkey
            primary key,
    created   timestamp    not null,
    removed   timestamp,
    updated   timestamp,
    uuid      varchar(255) not null
        constraint uk_user_uuid
            unique,
    email     varchar(255) not null
        constraint uk_user_email
            unique,
    full_name varchar(255) not null,
    password  varchar(255) not null,
    verified  boolean      not null
);
create sequence user_seq start 1 increment 1;

create table if not exists user_role
(
    type      varchar(31)  not null,
    id        bigint       not null
        constraint user_role_pkey
            primary key,
    created   timestamp    not null,
    removed   timestamp,
    updated   timestamp,
    user_role varchar(255) not null,
    user_id   bigint       not null
        constraint fk_user_id
            references user_
);
create sequence abstract_user_role_seq start 1 increment 1;

create table if not exists user_role_client_organization
(
    id                     bigint not null
        constraint user_role_client_organization_pkey
            primary key
        constraint fk_user_id
            references user_role,
    client_organization_id bigint not null
        constraint fk_client_organization_id
            references client_organization
);
create sequence user_role_slient_organization_seq start 1 increment 1;

create table if not exists user_role_organization
(
    id              bigint not null
        constraint user_role_organization_pkey
            primary key
        constraint fk_user_id
            references user_role,
    organization_id bigint not null
        constraint fk_organization_id
            references organization
);
create sequence user_role_organization_seq start 1 increment 1;

create table if not exists user_role_super_admin
(
    id bigint not null
        constraint user_role_super_admin_pkey
            primary key
        constraint fk_user_id
            references user_role
);
create sequence user_role_super_admin_seq start 1 increment 1;

create table if not exists settings_whitelist_ip
(
    id              bigint       not null
        constraint settings_whitelist_ip_pkey
            primary key,
    created         timestamp    not null,
    removed         timestamp,
    updated         timestamp,
    uuid            varchar(255) not null
        constraint uk_settings_whitelist_ip_uuid
            unique,
    ip              varchar(255),
    label           varchar(255),
    organization_id bigint       not null
        constraint fk_settings_whitelist_ip_organization_id
            references organization
);
create sequence whitelist_ip_seq start 1 increment 1;
-- end user

insert into template_email (id, created, removed, updated, uuid, template_name, type)
VALUES (nextval('template_email_seq'), now(), null, now(), 'fd75503c-2968-11ea-978f-2e728ce88125a', 'template_user_reset_password', 'FORGOT_PASSWORD');

insert into template_email (id, created, removed, updated, uuid, template_name, type)
VALUES (nextval('template_email_seq'), now(), null, now(), 'fd755190-2968-11ea-978f-2e728ce88125a', 'template_user_verification', 'USER_VERIFICATION');