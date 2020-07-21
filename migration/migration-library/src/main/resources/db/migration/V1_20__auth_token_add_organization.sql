alter table auth_token
    add column if not exists organization_id bigint;

alter table auth_token add constraint fk_auth_token_organization_id foreign key (organization_id) references organization;