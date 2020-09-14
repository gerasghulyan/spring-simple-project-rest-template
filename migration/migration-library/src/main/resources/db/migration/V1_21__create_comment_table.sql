create table comment
(
    type    varchar(31)       not null,
    id      int8              not null,
    created timestamp         not null,
    removed timestamp,
    updated timestamp,
    uuid    varchar(255)      not null,
    message varchar(10485760) not null,
    user_id int8              not null,
    primary key (id)
);
create table comment_product
(
    product_uuid varchar(255) not null,
    id           int8         not null,
    primary key (id)
);
create sequence abstract_comment_seq start 1 increment 1;
alter table comment add constraint uk_comment_uuid unique (uuid);
alter table comment add constraint fk_user_id foreign key (user_id) references user_;
alter table comment_product add constraint fk_comment_product_user_id foreign key (id) references comment;

