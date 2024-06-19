create table if not exists `user`
(
    id                 varchar(40) not null primary key,
    version            int(4),
    created_by         varchar(64),
    updated_by         varchar(64),
    created_on         timestamp,
    updated_on         timestamp,
    email              varchar(64),
    password           varchar(128),
    phone_number       varchar(16),
    reset_password_key varchar(128),
    first_name         varchar(16),
    last_name          varchar(16)
);

create table if not exists permission
(
    id   varchar(40) not null primary key,
    version            int(4),
    created_by         varchar(64),
    updated_by         varchar(64),
    created_on         timestamp,
    updated_on         timestamp,
    permission_type varchar(16)
);

create table if not exists user_permission
(
    id                 varchar(40) not null primary key,
    version            int(4),
    created_by         varchar(64),
    updated_by         varchar(64),
    created_on         timestamp,
    updated_on         timestamp,
    user_id       varchar(40),
    permission_id varchar(40)
);

create table if not exists tutor
(
    id                  varchar(40) not null primary key,
    version             int(4),
    created_by          varchar(64),
    updated_by          varchar(64),
    created_on          timestamp,
    updated_on          timestamp,
    user_id             varchar(40),
    introduction        varchar(1028),
    address             varchar(256),
    gender              varchar(8),
    verification_status varchar(16)
);

create table if not exists specialty
(
    id         varchar(40) not null primary key,
    version    int(4),
    created_by varchar(64),
    updated_by varchar(64),
    created_on timestamp,
    updated_on timestamp,
    name       varchar(64)
);

create table if not exists tutor_specialty
(
    id           varchar(40) not null primary key,
    version      int(4),
    created_by   varchar(64),
    updated_by   varchar(64),
    created_on   timestamp,
    updated_on   timestamp,
    tutor_id     varchar(40),
    specialty_id varchar(40)
);

create table if not exists student
(
    id                  varchar(40) not null primary key,
    version             int(4),
    created_by          varchar(64),
    updated_by          varchar(64),
    created_on          timestamp,
    updated_on          timestamp,
    user_id             varchar(40),
    introduction        varchar(1028),
    address             varchar(256),
    gender              varchar(8),
    verification_status varchar(16)
    );

insert into permission (id, permission_type) values (uuid(), "SUPER_ADMIN");
insert into permission (id, permission_type) values (uuid(), "TUTOR");
insert into permission (id, permission_type) values (uuid(), "STUDENT");