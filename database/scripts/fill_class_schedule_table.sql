insert into mgol.class_schedule (id, teacher_id, room, class_id, subject_id, period_id)
values (UUID(), (select u.id
                 from mgol.user u
                          join mgol.user_role ur on u.id = ur.user_id
                          join mgol.role r on r.id = ur.role_id
                 where r.name = 'TEACHER'
                 limit 1), 314,
        (select id from mgol.class where number = 10 and letter = 'А'),
        (select id from mgol.subject where name = 'Математика'), 1),
       (UUID(), (select u.id
                 from mgol.user u
                          join mgol.user_role ur on u.id = ur.user_id
                          join mgol.role r on r.id = ur.role_id
                 where r.name = 'TEACHER'
                 limit 1), 314,
        (select id from mgol.class where number = 10 and letter = 'А'),
        (select id from mgol.subject where name = 'Математика'), 2),
       (UUID(), (select u.id
                 from mgol.user u
                          join mgol.user_role ur on u.id = ur.user_id
                          join mgol.role r on r.id = ur.role_id
                 where r.name = 'TEACHER'
                 limit 1), 314,
        (select id from mgol.class where number = 10 and letter = 'Б'),
        (select id from mgol.subject where name = 'Математика'), 3),
       (UUID(), (select u.id
                 from mgol.user u
                          join mgol.user_role ur on u.id = ur.user_id
                          join mgol.role r on r.id = ur.role_id
                 where r.name = 'TEACHER'
                 limit 1), 314,
        (select id from mgol.class where number = 10 and letter = 'Б'),
        (select id from mgol.subject where name = 'Математика'), 3),
       (UUID(), (select u.id
                 from mgol.user u
                          join mgol.user_role ur on u.id = ur.user_id
                          join mgol.role r on r.id = ur.role_id
                 where r.name = 'TEACHER'
                 limit 1), 314,
        (select id from mgol.class where number = 10 and letter = 'А'),
        (select id from mgol.subject where name = 'Физика'), 8);

