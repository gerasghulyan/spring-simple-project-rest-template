alter table token_invitation_user
    rename to token_user_invitation_to_organization;

create table token_user_invitation_to_organization_client
(
    id                 int8 not null,
    user_invitation_id int8 not null,
    primary key (id)
);

alter table token_user_invitation_to_organization_client add constraint fk_token_invitation_user_invitation_user_id
    foreign key (user_invitation_id) references invitation_organization_client_user;

alter table token_user_invitation_to_organization_client add constraint fk_token_user_invitation_to_organization_client_token foreign key (id) references token;