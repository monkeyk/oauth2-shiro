


set names utf8;


--  Two users: admin/Admin@2015#     test/Test@2015#
truncate users;
insert into users(id,guid,create_time,username,password,password_salt,default_user)
values
(21,uuid(),now(),'admin','7242fb20c63882a6664742e1f9e1ed77e13b74d601cbe5fb11430d24768e808b','75fbe11d6f70e77b256121d7c3d5c412',1),
(22,uuid(),now(),'test','beef64b3218e0c93051119bde87782ed7b932169228c091464055369336f5044','5602aa9866ca612e66dbb7f7c9a1d3b7',0);


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
('test-client','Test@2015$$','Test Client','http://andaily.com',
'http://andaily.com/favicon.ico','os-resource','read write','authorization_code,password,refresh_token,client_credentials',
'http://localhost:7777/spring-oauth-client/authorization_code_callback','22');
-- Mobile resource client details
insert into oauth_client_details(client_id, client_secret, client_name, client_uri,
client_icon_uri, resource_ids, scope, grant_types,
redirect_uri, roles)
values
('mobile-client','Mobile@2015$$','Mobile Client','http://andaily.com',
'http://andaily.com/favicon.ico','mobile-resource','read write','password,refresh_token',
'http://localhost:7777/spring-oauth-client/authorization_code_callback','22');


