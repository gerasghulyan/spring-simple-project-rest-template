create table user_role_organization_admin (id int8 not null, organization_id int8 not null, primary key (id));
alter table user_role_organization_admin add constraint fk_organization_id foreign key (organization_id) references organization;
alter table user_role_organization_admin add constraint fk_user_role_organization_admin_user_role foreign key (id) references user_role;
alter table user_role_organization_admin add constraint uk_user_role_organization_admin_organization_id unique (organization_id);