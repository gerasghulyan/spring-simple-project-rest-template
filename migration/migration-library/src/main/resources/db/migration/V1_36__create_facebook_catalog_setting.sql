create sequence facebook_catalog_setting_seq start 1 increment 1;

create table if not exists facebook_catalog_setting
(
    id              bigint    not null
        constraint facebook_catalog_setting_pkey
            primary key,
    uuid            text      not null
        constraint uk_facebook_catalog_setting_uuid
            unique,
    created         timestamp not null,
    removed         timestamp,
    updated         timestamp,
    system_user_token text      not null,
    organization_id bigint    not null
        constraint fk_organization_id references organization,
    name            text      not null,
    catalog_id      text      not null
);

