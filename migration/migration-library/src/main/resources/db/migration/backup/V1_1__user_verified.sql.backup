alter table user_ add column if not exists verified boolean;

update user_ set verified = false;

alter table user_ alter column verified set not null;
