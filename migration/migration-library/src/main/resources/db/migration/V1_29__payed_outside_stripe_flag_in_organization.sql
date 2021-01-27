alter table organization
    add column payed_outside_stripe boolean;
alter table organization
    alter column payed_outside_stripe set default false;

