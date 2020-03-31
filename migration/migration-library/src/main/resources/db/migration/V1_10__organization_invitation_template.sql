CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

insert into template_email (id, created, removed, updated, uuid, template_name, type)
VALUES (nextval('template_email_seq'), now(), null, now(), public.uuid_generate_v4(), 'template_organization_invitation', 'ORGANIZATION_INVITATION');