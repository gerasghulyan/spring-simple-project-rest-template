create table token_reset_password
(
    id      int8 not null,
    user_id int8 not null,
    primary key (id)
);

alter table token_reset_password
    add constraint fk_user_id foreign key (user_id) references user_;

alter table token_reset_password
    add constraint fk_token_id foreign key (id) references token;