create extension if not exists "uuid-ossp";

create sequence abstract_token_seq start 1 increment 1;
create sequence abstract_user_role_seq start 1 increment 1;
create sequence client_organization_seq start 1 increment 1;
create sequence organization_seq start 1 increment 1;
create sequence template_email_seq start 1 increment 1;
create sequence user_seq start 1 increment 1;
create table client_organization
(
    id                       int8         not null,
    created                  timestamp    not null,
    removed                  timestamp,
    updated                  timestamp,
    uuid                     varchar(255) not null,
    client_organization_name varchar(255) not null,
    client_organization_slug varchar(255) not null,
    organization_id          int8         not null,
    primary key (id)
);
create table organization
(
    id                int8         not null,
    created           timestamp    not null,
    removed           timestamp,
    updated           timestamp,
    uuid              varchar(255) not null,
    organization_name varchar(255) not null,
    organization_slug varchar(255) not null,
    primary key (id)
);
create table template_email
(
    id            int8         not null,
    created       timestamp    not null,
    removed       timestamp,
    updated       timestamp,
    uuid          varchar(255) not null,
    template_name varchar(255) not null,
    type          varchar(255) not null,
    primary key (id)
);
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
create table token_reset_password
(
    id      int8 not null,
    user_id int8 not null,
    primary key (id)
);
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
create table user_role
(
    type      varchar(31)  not null,
    id        int8         not null,
    created   timestamp    not null,
    removed   timestamp,
    updated   timestamp,
    user_role varchar(255) not null,
    user_id   int8         not null,
    primary key (id)
);
create table user_role_client_organization
(
    id                     int8 not null,
    client_organization_id int8 not null,
    primary key (id)
);
create table user_role_organization
(
    id              int8 not null,
    organization_id int8 not null,
    primary key (id)
);
create table user_role_super_admin
(
    id int8 not null,
    primary key (id)
);
alter table client_organization
    add constraint UK_6a24i9wx6r7q4un12x7d0bj5e unique (uuid);
alter table client_organization
    add constraint UK_ha1179ul5wbqymo5h85uc0p6e unique (client_organization_slug);
alter table organization
    add constraint UK_s9xj0yg0stek0h7hedcn2qro3 unique (uuid);
alter table organization
    add constraint UK_5vap4rnqfqpyoybex4scqc4vt unique (organization_slug);
alter table template_email
    add constraint UK_template_email_type_template_name unique (type, template_name);
alter table template_email
    add constraint UK_7f25tjvnuyyk4ii03ww1ao13f unique (uuid);
alter table token
    add constraint UK_k74apnrvhamiburvb8hjwuh7h unique (uuid);
alter table token
    add constraint UK_pddrhgwxnms2aceeku9s2ewy5 unique (token);
alter table user_
    add constraint UK_133o0fu20xm8ocrtcwqm0a650 unique (uuid);
alter table user_
    add constraint UK_ha67cvlhy4nk1prswl5gj1y0y unique (email);
alter table client_organization
    add constraint fk_organization_id foreign key (organization_id) references organization;
alter table token_reset_password
    add constraint fk_user_id foreign key (user_id) references user_;
alter table token_reset_password
    add constraint FK78uvyaokg2yisefl8de518yuy foreign key (id) references token;
alter table user_role
    add constraint fk_user_id foreign key (user_id) references user_;
alter table user_role_client_organization
    add constraint fk_client_organization_id foreign key (client_organization_id) references client_organization;
alter table user_role_client_organization
    add constraint FK6jcr642b4j4up2i7jacbqswgk foreign key (id) references user_role;
alter table user_role_organization
    add constraint fk_organization_id foreign key (organization_id) references organization;
alter table user_role_organization
    add constraint FKrcic7bqu1apn97ovsds4nlhna foreign key (id) references user_role;
alter table user_role_super_admin
    add constraint FK3ittxgbixm7b3dm25v5dvw5av foreign key (id) references user_role;

insert into template_email (id, created, removed, updated, uuid, template_name, type)
VALUES (nextval('template_email_seq'), now(), null, now(), uuid_generate_v4(), 'template1', 'FORGOT_PASSWORD');