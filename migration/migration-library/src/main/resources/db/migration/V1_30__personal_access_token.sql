create sequence token_personal_access_seq start 1 increment 1;

create table if not exists token_personal_access
(
    id      int8   not null,
    user_id bigint not null,
    primary key (id)
);

alter table token_personal_access
    add constraint fk_token_personal_access_user_id foreign key (user_id) references user_;
