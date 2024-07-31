alter table user_profile add column user_type varchar(32);

update user_profile set user_type = 'STUDENT'
                    where user_id in (select user.id from user left join tutor.user_permission up on user.id = up.user_id
                                                left join permission on up.permission_id = permission.id
                                                where permission_type = 'STUDENT');
update user_profile set user_type = 'TUTOR'
where user_id in (select user.id from user left join tutor.user_permission up on user.id = up.user_id
                                           left join permission on up.permission_id = permission.id
                  where permission_type = 'TUTOR');
update user_profile set user_type = 'OPERATOR'
where user_id in (select user.id from user left join tutor.user_permission up on user.id = up.user_id
                                           left join permission on up.permission_id = permission.id
                  where permission_type = 'OPERATOR');
update user_profile set user_type = 'SUPER_ADMIN'
where user_id in (select user.id from user left join tutor.user_permission up on user.id = up.user_id
                                           left join permission on up.permission_id = permission.id
                  where permission_type = 'SUPER_ADMIN');