create table client_organization
(
    id                       int8         not null,
    created                  timestamp    not null,
    updated                  timestamp,
    uuid                     varchar(255) not null,
    client_organization_name varchar(255) not null,
    client_organization_slug varchar(255) not null,
    primary key (id)
);

alter table client_organization
    add constraint UK_client_organization_slug unique (client_organization_slug);