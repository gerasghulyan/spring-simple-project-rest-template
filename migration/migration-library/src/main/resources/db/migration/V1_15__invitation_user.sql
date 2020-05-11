create sequence invitation_user_seq start 1 increment 1;

create table invitation_user (
    id int8 not null,
    created timestamp not null,
    removed timestamp,
    updated timestamp,
    uuid varchar(255) not null,
    user_role varchar(255) not null,
    email varchar(255) not null,
    status varchar(255) not null,
    user_id   bigint    not null
        constraint fk_invitation_user_user_id
            references user_,
    primary key (id)
);

alter table invitation_user add constraint uk_invitation_user_uuid unique (uuid);