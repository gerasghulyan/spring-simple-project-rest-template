alter table organization
    add column invitation_organization_id bigint;
alter table organization
    add constraint fk_organization_invitation_organization_id foreign key (invitation_organization_id) references invitation_organization,
    add constraint uk_organization_invitation_organization_id unique (uuid);

alter table invitation_organization
    drop constraint fk_settings_whitelist_ip_organization_id;
alter table invitation_organization
    drop column organization_id;