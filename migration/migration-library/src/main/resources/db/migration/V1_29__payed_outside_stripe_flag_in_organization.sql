alter table organization
    add column payed_outside_stripe boolean;
alter table organization
    alter column payed_outside_stripe set default false;

update organization
set payed_outside_stripe = false
where organization.payed_outside_stripe is null;