create table if not exists user_profile
(
    id           varchar(40) not null primary key,
    version      int(4),
    created_by   varchar(64),
    updated_by   varchar(64),
    created_on   timestamp,
    updated_on   timestamp,
    user_id      varchar(40),
    email        varchar(64),
    phone_number varchar(16),
    first_name   varchar(16),
    last_name    varchar(16)
);

insert into user_profile
select uuid(),
       user.version,
       user.created_by,
       user.updated_by,
       user.created_on,
       user.updated_on,
       user.id,
       user.email,
       user.phone_number,
       user.first_name,
       user.last_name
from user;

alter table user
    drop column phone_number,
    drop column first_name,
    drop column last_name;