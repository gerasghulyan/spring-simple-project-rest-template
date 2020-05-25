alter table user_role_organization_admin
    drop constraint uk_user_role_organization_admin_organization_id;

create table token_invitation_user (
    id int8 not null,
    invitation_user_id int8 not null,
    primary key (id)
);

alter table token_invitation_user add constraint fk_token_invitation_user_invitation_user_id
    foreign key (invitation_user_id) references invitation_user;

alter table token_invitation_user add constraint fk_token_invitation_user_token foreign key (id) references token;