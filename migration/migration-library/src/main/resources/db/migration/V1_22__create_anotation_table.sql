create table annotation
(
    id           int8              not null,
    created      timestamp         not null,
    removed      timestamp,
    updated      timestamp,
    uuid         varchar(255)      not null,
    d1           float8            not null,
    d2           float8            not null,
    d3           float8            not null,
    number       int4              not null,
    product_uuid varchar(255)      not null,
    text         varchar(10485760) not null,
    user_id      int8              not null,
    primary key (id)
);
create sequence annotation_seq start 1 increment 1;
alter table annotation add constraint uk_annotation_uuid unique (uuid);
alter table annotation add constraint fk_user_id foreign key (user_id) references user_;
