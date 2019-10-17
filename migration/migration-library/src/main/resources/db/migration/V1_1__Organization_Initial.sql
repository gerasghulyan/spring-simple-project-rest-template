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

alter table organization
    add constraint UK_organization_uuid unique (uuid);

alter table organization
    add constraint UK_organization_slug unique (organization_slug);