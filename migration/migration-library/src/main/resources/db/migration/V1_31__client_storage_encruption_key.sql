create sequence storage_client_organization_key_seq start 1 increment 1;

create table storage_client_organization_key
(
    id                     int8         not null,
    created                timestamp    not null,
    removed                timestamp,
    updated                timestamp,
    name                   varchar(255) not null,
    type                   int4         not null,
    client_organization_id int8         not null,
    primary key (id)
);