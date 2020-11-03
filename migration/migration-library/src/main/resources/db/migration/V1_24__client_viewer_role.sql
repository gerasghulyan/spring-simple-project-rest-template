create sequence client_viewer_seq start 1 increment 1;

create table user_role_client_viewer_organization
(
    id                     int8 not null,
    client_organization_id int8 not null,
    primary key (id)
);

alter table user_role_client_viewer_organization
    add constraint fk_client_organization_id foreign key (client_organization_id) references client_organization;

alter table user_role_client_viewer_organization
    add constraint fk_user_role_organization_client_viewer_role foreign key (id) references user_role;