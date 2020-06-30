create sequence if not exists token_reset_password_seq start 1 increment 1;

create table if not exists token_reset_password (id int8 not null, user_id int8 not null, primary key (id));

alter table token_reset_password add constraint fk_token_reset_password_user_id foreign key (user_id) references user_;
alter table token_reset_password add constraint fk_token_reset_password_token foreign key (id) references token;