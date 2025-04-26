create type status as enum('ACTIVE', 'INACTIVE', 'DRAFT', 'DELETED', 'ARCHIVED');

create table if not exists categories(
    id bigint primary key,
    parent_category_id bigint,
    version int,
    created_date date default current_date ,
    modified_date date,
    code varchar(50) not null default 'unassigned',
    name varchar(200) not null,
    deleted boolean,
    status varchar(50) not null default 'ACTIVE'
);

alter table categories add constraint parent_category foreign key(parent_category_id) references categories(id);

create sequence categories_seq increment 50;