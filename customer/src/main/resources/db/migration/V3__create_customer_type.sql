create table if not exists customer_type
(
    id          bigserial      not null,
    name      varchar(60) not null,
    primary key (id)
)