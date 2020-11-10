alter table user_role alter column type type character varying(120);

update user_role
set type = 'CLIENT_ORGANIZATION_ADMIN_ROLE'
where type = 'CLIENT_ROLE';

alter sequence user_role_slient_organization_seq rename to user_role_client_admin_seq;

ALTER TABLE user_role_client_organization
    RENAME TO user_role_client_organization_admin;

create sequence user_role_client_organization_content_manager_seq start 1 increment 1;

create table user_role_client_organization_content_manager
(
    id                     bigint not null,
    client_organization_id bigint not null,
    primary key (id)
);

alter table user_role_client_organization_content_manager
    add constraint fk_client_organization_id foreign key (client_organization_id) references client_organization;

alter table user_role_client_organization_content_manager
    add constraint fk_user_id foreign key (id) references user_role;

create sequence user_role_client_organization_viewer_seq start 1 increment 1;

create table user_role_client_organization_viewer
(
    id                     bigint not null,
    client_organization_id bigint not null,
    primary key (id)
);

alter table user_role_client_organization_viewer
    add constraint fk_client_organization_id foreign key (client_organization_id) references client_organization;

alter table user_role_client_organization_viewer
    add constraint fk_user_id foreign key (id) references user_role;