create table if not exists sale_order(

    id bigserial not null primary key,
    number varchar(255),
    customer bigint,
    date DATE,
    id_status bigserial     not null,
    category integer,
    total double PRECISION,
    constraint fk_status foreign key (id_status) references status (id)
);