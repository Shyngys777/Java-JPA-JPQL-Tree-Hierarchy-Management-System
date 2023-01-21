drop table if exists tree;

create table tree
(
    id        serial8,
    left_key  int4    not null,
    right_key int4    not null,
    level     int2    not null,
    name      varchar not null,
    primary key (id)
);

--         // Коплектующие
--         // - Процессоры
--         // - - Intel
--         // - - AMD
--         // - ОЗУ
--         // Аудиотехника
--         // - Наушники
--         // - - С микрофоном
--         // - - Без микрофона
insert into tree(left_key, right_key, level, name)
values (1, 10, 0, 'Комплектующие'),
       (2, 7, 1, 'Процессоры'),
       (3, 4, 2, 'Intel'),
       (5, 6, 2, 'AMD'),
       (8, 9, 1, 'Озу'),
       (11, 18, 0, 'Аудиотехника'),
       (12, 17, 1, 'Наушники'),
       (13, 14, 2, 'С микрофоном'),
       (15, 16, 2, 'Без микрофона');

