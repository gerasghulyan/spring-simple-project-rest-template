alter table organization
    add column paid_outside_stripe boolean;
alter table organization
    alter column paid_outside_stripe set default false;

update organization
set paid_outside_stripe = false
where organization.paid_outside_stripe is null;