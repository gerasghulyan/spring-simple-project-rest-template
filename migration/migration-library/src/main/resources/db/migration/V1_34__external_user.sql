alter table user_
    alter column email drop not null;
alter table user_
    alter column password drop not null;

alter table user_
    add column source text not null default 'INTERNAL';