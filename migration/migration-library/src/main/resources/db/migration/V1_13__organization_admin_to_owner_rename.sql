ALTER INDEX user_role_organization_pkey rename to  user_role_organization_owner_pkey;

alter table user_role_organization rename to user_role_organization_owner;
alter sequence user_role_organization_seq rename to user_role_organization__owner_seq;
update user_role set type = 'ORGANIZATION_OWNER_ROLE', user_role = 'ORGANIZATION_OWNER' where type = 'ORGANIZATION_ROLE';

alter table user_role_organization_owner
    add constraint uk_user_role_organization_owner_organization_id unique (organization_id);