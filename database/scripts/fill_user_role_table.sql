insert into mgol.user_role (user_id, role_id)
select u.id, r.id
from mgol.user u
         cross join mgol.role r
where r.name = 'TEACHER'
limit 1;