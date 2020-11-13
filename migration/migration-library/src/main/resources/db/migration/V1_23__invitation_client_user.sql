alter table invitation_user rename to invitation_organization_user;
alter table invitation_organization_user rename constraint fk_invitation_user_user_id to fk_invitation_organization_user_user_id;
alter table invitation_organization_user rename constraint fk_invitation_user_organization_id to fk_invitation_organization_user_organization_id;
alter table invitation_organization_user rename constraint uk_invitation_user_uuid to uk_invitation_organization_user_uuid;
alter sequence invitation_user_seq rename to invitation_organization_user_seq;

create sequence invitation_organization_client_user_seq start 1 increment 1;

create table invitation_organization_client_user (
    id          int8 not null,
    created     timestamp not null,
    removed     timestamp,
    updated     timestamp,
    uuid        varchar(255) not null,
    user_role   varchar(255) not null,
    email       varchar(255) not null,
    status      varchar(255) not null,
    user_id     bigint not null
        constraint fk_invitation_client_user_user_id
            references user_,
    organization_client_id bigint not null
        constraint fk_invitation_client_user_organization_user_id
            references client_organization,
    primary key (id)
);

alter table invitation_organization_client_user add constraint uk_invitation_client_user_uuid unique (uuid);
