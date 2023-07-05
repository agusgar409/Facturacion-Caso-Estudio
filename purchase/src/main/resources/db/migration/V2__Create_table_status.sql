CREATE TABLE status
(
    id          BIGSERIAL NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_status PRIMARY KEY (id)
);

insert into status (id, name)
values (1, 'confirmado'),
       (2, 'borrador');