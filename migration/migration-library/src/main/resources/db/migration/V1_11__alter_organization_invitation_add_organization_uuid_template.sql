alter table invitation_organization
    add column organization_id bigint;
alter table invitation_organization add constraint fk_settings_whitelist_ip_organization_id foreign key (organization_id) references organization;