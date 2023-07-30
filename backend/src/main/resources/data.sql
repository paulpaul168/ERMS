CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
insert into beacon(id, x, y, z, lat, long)
values ('1', 3.0, 4.0, 0.0, 34240.0, 343242.0);
insert into beacon(id, x, y, z, lat, long)
values ('2', 7.0, 4.0, 0.0, 4231.0, 4212.0);
insert into beacon(id, x, y, z, lat, long)
values ('3', 1.0, 1.0, 0.0, 1938.0, 23410.0);
insert into person(id, name)
values (uuid_generate_v4(), 'Rudi');
insert into person(id, name)
values (uuid_generate_v4(), 'Sieghard');
insert into person(id, name)
values (uuid_generate_v4(), 'Franz');
insert into person(id, name)
values (uuid_generate_v4(), 'Paul');
