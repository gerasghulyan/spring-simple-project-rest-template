create table user_client_organization_role
(
    id                     int8         not null,
    created                timestamp    not null,
    removed                timestamp,
    updated                timestamp,
    user_role              varchar(255) not null,
    client_organization_id int8         not null,
    user_id                int8         not null,
    primary key (id)
);

alter table user_client_organization_role
    add constraint uk_user_id_and_client_organization_id unique (client_organization_id, user_id);

alter table user_client_organization_role
    add constraint fk_client_organization_id foreign key (client_organization_id) references client_organization;

alter table user_client_organization_role
    add constraint fk_user_id foreign key (user_id) references user_;