create table if not exists customer
(

    id           bigserial       not null,
    dni          bigint not null,
    email varchar (255) not null,
    name         varchar(60)  not null,
    lastname     varchar(60)  not null,
    id_direction bigserial     not null,
    primary key (id),
    constraint fk_direction foreign key (id_direction) references direction(id)

)