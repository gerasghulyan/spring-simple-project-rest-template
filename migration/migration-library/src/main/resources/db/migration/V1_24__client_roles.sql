create sequence user_role_client_admin_seq start 1 increment 1;

create table user_role_client_admin
(
    id                     int8 not null,
    client_organization_id int8 not null,
    primary key (id)
);

alter table user_role_client_admin
    add constraint fk_client_organization_id foreign key (client_organization_id) references client_organization;

alter table user_role_client_admin
    add constraint fk_user_role_client_admin_role foreign key (id) references user_role;



create sequence user_role_client_content_manager_seq start 1 increment 1;

create table user_role_client_content_manager
(
    id                     int8 not null,
    client_organization_id int8 not null,
    primary key (id)
);

alter table user_role_client_content_manager
    add constraint fk_client_organization_id foreign key (client_organization_id) references client_organization;

alter table user_role_client_content_manager
    add constraint fk_user_role_client_content_manager_role foreign key (id) references user_role;



create sequence user_role_client_viewer_seq start 1 increment 1;

create table user_role_client_viewer
(
    id                     int8 not null,
    client_organization_id int8 not null,
    primary key (id)
);

alter table user_role_client_viewer
    add constraint fk_client_organization_id foreign key (client_organization_id) references client_organization;

alter table user_role_client_viewer
    add constraint fk_user_role_client_viewer_role foreign key (id) references user_role;