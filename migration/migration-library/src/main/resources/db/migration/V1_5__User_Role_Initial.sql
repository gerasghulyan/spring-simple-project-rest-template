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

alter table user_role
    add constraint fk_user_id foreign key (user_id) references user_;

alter table user_role_client_organization
    add constraint fk_client_organization_id foreign key (client_organization_id) references client_organization;

alter table user_role_client_organization
    add constraint fk_user_role_id foreign key (id) references user_role;

alter table user_role_organization
    add constraint fk_organization_id foreign key (organization_id) references organization;

alter table user_role_organization
    add constraint fk_user_role_id foreign key (id) references user_role;

alter table user_role_super_admin
    add constraint fk_user_role_id foreign key (id) references user_role;