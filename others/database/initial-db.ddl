


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

