create sequence invitation_organization_seq start 1 increment 1;

create table invitation_organization (
    id int8 not null,
    created timestamp not null,
    removed timestamp,
    updated timestamp,
    uuid varchar(255) not null,
    customer_subscription_definition_uuid varchar(255) not null,
    email varchar(255) not null,
    organization_name varchar(255) not null,
    owner_full_name varchar(255) not null,
    slug varchar(255) not null,
    status varchar(255) not null,
    primary key (id)
);

alter table invitation_organization add constraint uk_invitation_organization_uuid unique (uuid);

