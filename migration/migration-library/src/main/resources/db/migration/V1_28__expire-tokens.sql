update token_authentication
set expiration = now()
where expiration is null OR expiration >= now();