


set names utf8;


--  Two users: admin/admin;   test/test
truncate users;
insert into users(id,guid,create_time,username,password,default_user)
values
(21,uuid(),now(),'admin','21232f297a57a5a743894a0e4a801fc3',1),
(22,uuid(),now(),'test','098f6bcd4621d373cade4e832627b4f6',0);


