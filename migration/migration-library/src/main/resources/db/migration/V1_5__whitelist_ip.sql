create sequence whitelist_ip_seq start 1 increment 1;
create table whitelist_ip (id int8 not null,
    created timestamp not null,
    removed timestamp,
    updated timestamp,
    uuid varchar(255) not null,
    ip varchar(255),
    label varchar(255),
    organization_id int8 not null,
    primary key (id)
);

alter table whitelist_ip add constraint uk_whitelist_ip_uuid unique (uuid);

alter table whitelist_ip add constraint fk_whitelist_ip_organization_id foreign key (organization_id) references organization;