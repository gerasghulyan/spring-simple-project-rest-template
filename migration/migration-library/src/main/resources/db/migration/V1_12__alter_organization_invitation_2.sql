alter table organization
    add column invitation_organization_id bigint;
alter table organization
    add constraint fk_organization_invitation_organization_id foreign key (invitation_organization_id) references invitation_organization,
    add constraint uk_organization_invitation_organization_id unique (invitation_organization_id);

alter table invitation_organization
    drop constraint if exists fk_settings_whitelist_ip_organization_id;
alter table invitation_organization
    drop column if exists organization_id;