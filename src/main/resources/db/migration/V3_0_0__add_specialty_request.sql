create table if not exists specialty_demand
(
    id             varchar(40) not null primary key,
    version        int(4),
    created_by     varchar(64),
    updated_by     varchar(64),
    created_on     timestamp,
    updated_on     timestamp,
    specialty_name varchar(64),
    status         varchar(16)
);