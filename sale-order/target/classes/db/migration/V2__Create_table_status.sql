
create table if not exists status
(
    id   bigserial      not null,
    name varchar(255)  not null,
    primary key (id)
);

insert into status (id, name)
values (1, 'CONFIRMADA'),
       (2, 'BORRADOR'),
       (3, 'DEVUELTA');
