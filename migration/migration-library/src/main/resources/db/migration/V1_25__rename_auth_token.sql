alter table auth_token
    add column if not exists client_organization_id bigint,
    add constraint fk_token_authentication_client_organization_id
        foreign key (client_organization_id) references client_organization;
alter table auth_token
    drop constraint uk_auth_token_token,
    drop constraint uk_auth_token_uuid;
alter table auth_token
    rename constraint fk_auth_token_user_id to fk_token_authentication_user_id;
alter table auth_token
    rename constraint fk_auth_token_organization_id to fk_token_authentication_organization_id;
alter table auth_token
    rename constraint auth_token_pkey to token_authentication_pkey;
alter table auth_token
    rename to token_authentication;

update token
set type = 'AUTHENTICATION'
where type is null;