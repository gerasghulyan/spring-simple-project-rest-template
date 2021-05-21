alter table settings_whitelist_ip
    add column type varchar(255) not null default 'EMBEDDED';