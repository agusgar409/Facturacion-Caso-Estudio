create table if not exists customer_customer_type
(
    customer_id bigserial not null,
    type_id     bigserial not null,
    constraint fk_customer foreign key (customer_id) references customer (id),
    constraint fk_type foreign key (type_id) references customer_type (id)
)