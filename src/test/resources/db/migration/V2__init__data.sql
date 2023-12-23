insert into public.profile values (nextval('profile_id_seq'),'ADMIN', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', true);
insert into public.profile values (nextval('profile_id_seq'),'test2', 'qwerty1', true);
insert into public.profile values (nextval('profile_id_seq'),'test3', 'asdfg', true);
insert into public.profile values (nextval('profile_id_seq'),'test4', '12345', false);
insert into public.profile values (nextval('profile_id_seq'), 'test5', 'qwer098', false);

insert into public.role(id, name, description) values (1, 'ADMIN', 'Роль админа');
insert into public.role(id, name, description) values (2, 'USER', 'Роль пользователя, располагает всеми основными возможностями');

insert into public.profile_role(profile_id, role_id) values (1, 1);
insert into public.profile_role(profile_id, role_id) values (1, 2);

insert into permission(id, description, granted_authority) VALUES (1, 'Поиск сканированного текста', 'scan.search');
insert into permission(id, description, granted_authority) VALUES (2, 'Создание скана', 'scan.create');
insert into permission(id, description, granted_authority) VALUES (3, 'Удаление скана', 'scan.delete');
insert into permission(id, description, granted_authority) VALUES (4, 'Восстановление скана', 'scan.restore');
insert into permission(id, description, granted_authority) VALUES (5, 'Получение файла по его id', 'file.read');
insert into permission(id, description, granted_authority) VALUES (6, 'Добавление пользователя', 'profile.add');
insert into permission(id, description, granted_authority) VALUES (7, 'Обновление данных пользователя', 'profile.update');
insert into permission(id, description, granted_authority) VALUES (8, 'Удаление пользователя', 'profile.delete');
insert into permission(id, description, granted_authority) VALUES (9, 'Блокировка пользователя', 'profile.block');
insert into permission(id, description, granted_authority) VALUES (10, 'Добавление роли', 'role.add');
insert into permission(id, description, granted_authority) VALUES (11, 'Обновление данных роли', 'role.update');
insert into permission(id, description, granted_authority) VALUES (12, 'Удаление роли', 'role.delete');
insert into permission(id, description, granted_authority) VALUES (13, 'Добавление привилегии', 'permission.add');
insert into permission(id, description, granted_authority) VALUES (14, 'Обновление данных привилегии', 'permission.update');
insert into permission(id, description, granted_authority) VALUES (15, 'Удаление привилегии', 'permission.delete');

insert into role_permission(role_id, permission_id) VALUES (1, 1);
insert into role_permission(role_id, permission_id) VALUES (1, 2);
insert into role_permission(role_id, permission_id) VALUES (1, 3);
insert into role_permission(role_id, permission_id) VALUES (1, 4);
insert into role_permission(role_id, permission_id) VALUES (1, 5);
insert into role_permission(role_id, permission_id) VALUES (1, 6);
insert into role_permission(role_id, permission_id) VALUES (1, 7);
insert into role_permission(role_id, permission_id) VALUES (1, 8);
insert into role_permission(role_id, permission_id) VALUES (1, 9);
insert into role_permission(role_id, permission_id) VALUES (1, 10);
insert into role_permission(role_id, permission_id) VALUES (1, 11);
insert into role_permission(role_id, permission_id) VALUES (1, 12);
insert into role_permission(role_id, permission_id) VALUES (1, 13);
insert into role_permission(role_id, permission_id) VALUES (1, 14);
insert into role_permission(role_id, permission_id) VALUES (1, 15);

insert into role_permission(role_id, permission_id) VALUES (2, 1);
insert into role_permission(role_id, permission_id) VALUES (2, 2);
insert into role_permission(role_id, permission_id) VALUES (2, 6);