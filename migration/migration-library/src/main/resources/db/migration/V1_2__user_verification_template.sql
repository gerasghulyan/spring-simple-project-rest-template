insert into template_email (id, created, removed, updated, uuid, template_name, type)
VALUES (nextval('template_email_seq'), now(), null, now(), uuid_generate_v4(), 'template_user_verification', 'USER_VERIFICATION');