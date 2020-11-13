alter sequence auth_token_seq rename to token_authentication_seq;
alter table auth_token
    rename to token_authentication;
alter table token_authentication
    add column if not exists client_organization_id bigint,
    add constraint fk_token_authentication_client_organization_id
        foreign key (client_organization_id) references client_organization;
alter table token_authentication
    rename constraint uk_auth_token_token to uk_token_authentication_token;
alter table token_authentication
    rename constraint uk_auth_token_uuid to uk_token_authentication_uuid;
alter table token_authentication
    rename constraint fk_auth_token_user_id to fk_token_authentication_user_id;
alter table token_authentication
    rename constraint fk_auth_token_organization_id to fk_token_authentication_organization_id;
alter table token_authentication
    rename constraint auth_token_pkey to token_authentication_pkey;
