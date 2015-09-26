


set names utf8;


--  Two users: admin/admin;   test/test
truncate users;
insert into users(id,guid,create_time,username,password,default_user)
values
(21,uuid(),now(),'admin','21232f297a57a5a743894a0e4a801fc3',1),
(22,uuid(),now(),'test','098f6bcd4621d373cade4e832627b4f6',0);


--  Two roles:  User,Admin
truncate roles;
insert into roles(id,guid,create_time,role_name)
values
(21,uuid(),now(),'Admin'),
(22,uuid(),now(),'User');


--  Four permissions:  user:create,user:edit,user:list,user:delete
truncate roles_permissions;
insert into roles_permissions(roles_id, permission)
values
(21,'user:create'),
(21,'user:edit'),
(21,'user:list'),
(21,'user:delete'),
(22,'user:list');


-- user_roles
truncate user_roles;
insert into user_roles(users_id,roles_id)
values
(21,21),
(22,22);




--  Initial Oauth
--  oauth_client_details
truncate oauth_client_details;
insert into oauth_client_details(client_id, client_secret, client_name, client_uri,
client_icon_uri, resource_ids, scope, grant_types,
redirect_uri, roles)
values
('test','test','Test Client','http://andaily.com',
'http://andaily.com/favicon.ico','os-resource','read write','authorization_code,password,refresh_token,client_credentials',
'http://localhost:7777/spring-oauth-client/authorization_code_callback','22');
-- Mobile resource client details
insert into oauth_client_details(client_id, client_secret, client_name, client_uri,
client_icon_uri, resource_ids, scope, grant_types,
redirect_uri, roles)
values
('mobile','mobile','Mobile Client','http://andaily.com',
'http://andaily.com/favicon.ico','mobile-resource','read write','password,refresh_token',
'http://localhost:7777/spring-oauth-client/authorization_code_callback','22');


