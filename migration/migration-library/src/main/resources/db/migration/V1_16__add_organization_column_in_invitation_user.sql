alter table invitation_user
    add column organization_id bigint not null;
alter table invitation_user add constraint fk_invitation_user_organization_id foreign key (organization_id) references organization;