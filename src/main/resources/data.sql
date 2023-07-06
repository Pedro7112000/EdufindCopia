insert into category (id, name) values (1, "Base de datos"), (2, "Backend"), (3, "Frontend"), (4, "Cloud Computing"), (5, "DevOps"), (6, "Ofimática"), (7, "Otros");

insert into role (id, name) values (1, "ROLE_ADMIN"), (2, "ROLE_USER"), (3, "ROLE_CENTER");


insert into user (type, id, password, status, username, name, ruc, phone)
values ("C", 1, "$2a$10$moL91es4MRuDvKI1hBhCa.7bIJ2.J8JT7K96jFEQrfLs9zGGETyqS", 1, "centro@demo.com", "Centro 1", "20600614861", "999999999");
insert into user_role (user_id, role_id) values (1, 3);

insert into user (type, id, password, status, username, first_name, last_name)
values ("P", 2, "$2a$10$moL91es4MRuDvKI1hBhCa.7bIJ2.J8JT7K96jFEQrfLs9zGGETyqS", 1, "jcalle@demo.com", "Jose", "Calle");

insert into user_role (user_id, role_id) values (2, 1);

insert into user (type, id, password, status, username, first_name, last_name)
values ("P", 3, "$2a$10$moL91es4MRuDvKI1hBhCa.7bIJ2.J8JT7K96jFEQrfLs9zGGETyqS", 1, "motoya@demo.com", "Miguel", "Otoya");

insert into user_role (user_id, role_id) values (3, 1);

insert into user (type, id, password, status, username, first_name, last_name)
values ("P", 4, "$2a$10$moL91es4MRuDvKI1hBhCa.7bIJ2.J8JT7K96jFEQrfLs9zGGETyqS", 1, "aasencio@demo.com", "Aldair", "Asencio");

insert into user_role (user_id, role_id) values (4, 1);

insert into course (name, schedule, start_date, duration, status, url, category_id, training_center_id)
values ("Python desde cero", "LUN 08:00 - 12:00", "2022/10/10", 24, 1, "URL", 2, 1);

insert into course (name, schedule, start_date, duration, status, url, category_id, training_center_id)
values ("PHP desde cero", "JUE 08:00 - 12:00", "2022/10/21", 16, 1, "URL", 2, 1);

insert into course (name, schedule, start_date, duration, status, url, category_id, training_center_id)
values ("NodeJS desde cero", "LUN MIE 08:00 - 12:00", "2022/10/26", 24, 1, "URL", 2, 1);

insert into course (name, schedule, start_date, duration, status, url, category_id, training_center_id)
values (".NET desde cero", "LUN 08:00 - 12:00", "2022/10/19", 20, 1, "URL", 2, 1);

insert into course (name, schedule, start_date, duration, status, url, category_id, training_center_id)
values ("Excel Básico", "LUN MAR 08:00 - 12:00", "2022/10/25", 16, 1, "URL", 6, 1);

insert into course (name, schedule, start_date, duration, status, url, category_id, training_center_id)
values ("AWS Essentials", "SAB 08:00 - 12:00", "2022/10/17", 32, 1, "URL", 4, 1);

insert into course (name, schedule, start_date, duration, status, url, category_id, training_center_id)
values ("Scrum Master", "DOM 08:00 - 12:00", "2022/10/10", 16, 1, "URL", 7, 1);


commit;