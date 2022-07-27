create table if not exists PRODUCT (
    id serial not null,
    name varchar(50) not null,
    description varchar(50) not null,
    amount int not null,
    price float(25) not null
);