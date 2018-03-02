insert into colors (id, code) values (1000, 'Black');
insert into colors (id, code) values (1001, 'White');
insert into colors (id, code) values (1002, 'Yellow');
insert into colors (id, code) values (1003, 'Blue');
insert into colors (id, code) values (1004, 'Red');
insert into colors (id, code) values (1005, 'Purple');
insert into colors (id, code) values (1006, 'Gray');
insert into colors (id, code) values (1007, 'Green');
insert into colors (id, code) values (1008, 'Pink');
insert into colors (id, code) values (1009, 'Gold');
insert into colors (id, code) values (1010, 'Silver');
insert into colors (id, code) values (1011, 'Orange');
insert into colors (id, code) values (1012, 'Brown');
insert into colors (id, code) values (1013, '256');

insert into phones (id, brand, model) values (1001, 'Brand1001', 'Model1001');
insert into phones (id, brand, model) values (1, 'Brand1', 'Model1');
insert into phones (id, brand, model) values (2, 'Brand2', 'Model2');
insert into phones (id, brand, model) values (345, 'Brand345', 'Model345');


insert into phone2color (phoneId, colorId) values (1, 1000);
insert into phone2color (phoneId, colorId) values (1, 1009);
insert into phone2color (phoneId, colorId) values (1, 1010);
insert into phone2color (phoneId, colorId) values (1001, 1000);
insert into phone2color (phoneId, colorId) values (1001, 1001);


insert into stocks (phoneId, stock, reserved) values (1, 2, 0);
insert into stocks (phoneId, stock, reserved) values (1001, 2, 0);
insert into stocks (phoneId, stock, reserved) values (2, 2, 0);
insert into stocks (phoneId, stock, reserved) values (345, 0, 0);