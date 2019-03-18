insert into colors (id, code) values (1000, 'Black');
insert into colors (id, code) values (1001, 'White');
insert into colors (id, code) values (1002, 'Yellow');
insert into colors (id, code) values (1003, 'Blue');
insert into colors (id, code) values (1004, 'Red');

insert into phones (id, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, description) values ('1000', 'ARCHOS', '0', 10.5, null, null, null, null, null, null, null, null,null,null, null, null, null, null, null, null, null, null, null, null, null, null);
insert into phones (id, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, description) values ('1001', 'ARCHOS', '1', 20.5, null, null, null, null, null, null, null, null,null,null, null, null, null, null, null, null, null, null, null, null, null, null);
insert into phones (id, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, description) values ('1002', 'ARCHOS', '2', 30.5, null, null, null, null, null, null, null, null,null,null, null, null, null, null, null, null, null, null, null, null, null, null);
insert into phones (id, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, description) values ('1003', 'ARCHOS', '3', 40.5, null, null, null, null, null, null, null, null,null,null, null, null, null, null, null, null, null, null, null, null, null, null);

insert into phone2color (phoneId, colorId) values (1000, 1000);
insert into phone2color (phoneId, colorId) values (1000, 1003);
insert into phone2color (phoneId, colorId) values (1001, 1001);
insert into phone2color (phoneId, colorId) values (1002, 1000);

insert into stocks (phoneId, stock, reserved) values (1000, 0, 0);
insert into stocks (phoneId, stock, reserved) values (1001, 11, 0);
insert into stocks (phoneId, stock, reserved) values (1002, 12, 1);
insert into stocks (phoneId, stock, reserved) values (1003, 13, 2);

insert into orders (orderId, secureId, firstName, lastName, subtotal, deliveryPrice, totalPrice, deliveryAddress, additionalInfo, contactPhoneNo, orderStatus) values ('1', 'qwerty', 'Vlad', 'Sheremet', 500.5, 5.5, 505.5, 'Minsk', 'I am Vlad', '375291596658', 'NEW');
insert into orders (orderId, secureId, firstName, lastName, subtotal, deliveryPrice, totalPrice, deliveryAddress, additionalInfo, contactPhoneNo, orderStatus) values ('2', 'qwerty1', 'Vlad', 'Sheremet', 500.5, 5.5, 505.5, 'Minks', 'I am Vlad', '375291596658', 'NEW');

insert into orderItems (orderId, phoneId, quantity) values ('2', '1001', '3');
insert into orderItems (orderId, phoneId, quantity) values ('2', '1002', '2');
insert into orderItems (orderId, phoneId, quantity) values ('2', '1003', '1');