INSERT INTO status (name)
VALUES ('confirmado'),
       ('borrador');

INSERT INTO purchase_order (number, customer, date, status, category, total)
VALUES ('F-2022-7-1', 1, current_date, 1, 2, 500),
       ('F-2022-7-2', 2, current_date, 1, 2, 500),
       ('F-2022-7-3', 3, current_date, 1, 2, 500),
       ('F-2022-7-4', 4, current_date, 1, 2, 600),
       ('F-2022-7-5', 5, current_date, 1, 2, 800),
       ('F-2022-7-6', 1, current_date, 2, 2, 900),
       ('F-2022-7-7', 3, current_date, 1, 2, 100),
       ('F-2022-7-8', 1, current_date, 2, 2, 1000),
       ('F-2022-7-9', 6, current_date, 1, 2, 100);