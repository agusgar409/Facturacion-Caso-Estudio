create table if not exists invoice(

    id_invoice bigint not null primary key,
    number varchar(255),
    dni_customer bigint,
    creation_date DATE,
    status bigint,
    category integer,
    description varchar(255),
    total_price double PRECISION

);