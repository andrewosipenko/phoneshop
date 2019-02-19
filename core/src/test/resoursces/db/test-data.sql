insert into colors (id, code) values (1000, 'Black');
insert into colors (id, code) values (1001, 'White');
insert into colors (id, code) values (1002, 'Yellow');
insert into colors (id, code) values (1003, 'Blue');
insert into colors (id, code) values (1004, 'Red');

insert into phones (id, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, description) values ('1000', 'ARCHOS', '0', null, null, null, null, null, null, null, null, null,null,null, null, null, null, null, null, null, null, null, null, null, null, null);
insert into phones (id, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, description) values ('1001', 'ARCHOS', '1', null, null, null, null, null, null, null, null, null,null,null, null, null, null, null, null, null, null, null, null, null, null, null);
insert into phones (id, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, description) values ('1002', 'ARCHOS', '2', null, null, null, null, null, null, null, null, null,null,null, null, null, null, null, null, null, null, null, null, null, null, null);
insert into phones (id, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, description) values ('1003', 'ARCHOS', '3', null, null, null, null, null, null, null, null, null,null,null, null, null, null, null, null, null, null, null, null, null, null, null);

insert into phone2color (phoneId, colorId) values (1000, 1000);
insert into phone2color (phoneId, colorId) values (1000, 1003);
insert into phone2color (phoneId, colorId) values (1001, 1001);
insert into phone2color (phoneId, colorId) values (1002, 1000);

insert into stocks (phoneId, stock, reserved) values (1000, 0, 0);
insert into stocks (phoneId, stock, reserved) values (1001, 11, 0);
insert into stocks (phoneId, stock, reserved) values (1002, 12, 1);
insert into stocks (phoneId, stock, reserved) values (1003, 13, 2);