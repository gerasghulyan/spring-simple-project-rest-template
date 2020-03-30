create table token_invitation_organization (
    id int8 not null,
    invitation_organization_id int8 not null,
    primary key (id)
);

alter table token_invitation_organization add constraint fk_token_invitation_organization_invitation_organization_id
    foreign key (invitation_organization_id) references invitation_organization;

alter table token_invitation_organization add constraint fk_token_invitation_organization_token foreign key (id) references token;