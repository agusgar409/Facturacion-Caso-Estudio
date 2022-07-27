create table if not exists purchase_order
(
    id       bigserial not null primary key,
    number   varchar(255),
    customer bigint,
    date     DATE,
    status   bigint,
    category integer,
    total    double PRECISION
);