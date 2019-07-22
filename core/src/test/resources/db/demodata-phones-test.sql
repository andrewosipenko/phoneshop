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

insert into phones (id, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, description) values ('1101', 'Acer', 'Acer Liquid E3', null, 4.7, 156, 138.0, 69.0, 8.9, '2014-02-20 00:00:00', 'Smart phone', 'Android (4.2.2)', '720 x  1280', 312, 'IPS LCD', 13.0, 2.0, 1.0, 4.0, null, 5.0, 260.0, 'Yes', 'GPS, A-GPS', 'manufacturer/Acer/Acer Liquid E3.jpg', 'The Acer Liquid E3 will cost €199 ($275 USD) and features a 4.7 inch screen with a 720 x 1280 resolution. A quad-core 1.2GHz processor is under the hood with 1GB of RAM on board and 4GB of native storage. The rear and front-facing snappers weigh in at 13MP and 2MP respectively, making this the model for the struggling (or penny wise) photographer.');
insert into phones (id, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, description) values ('1744', 'BLU', 'BLU Energy Diamond Mini', null, 4.0, 156, 131.5, 63.8, 11.3, null, 'Smart phone', 'Android (5.1)', '480 x  800', 233, 'IPS LCD', 5.0, 2.0, 0.5, 4.0, 3000, 72.0, 700.0, '4.0', 'GPS, A-GPS', 'manufacturer/BLU/BLU Energy Diamond Mini.jpg', 'The BLU Energy Diamond Mini sports a 4-inch display, 1.3 GHz quad-core processor, 512 MB of RAM, 4 GB of storage and a 5-megapixel camera.');
insert into phones (id, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, description) values ('1999', 'BlackBerry', 'BlackBerry Curve 9380', 335, 3.2, 156, 109.0, 60.0, 11.2, '2011-11-15 00:00:00', 'Smart phone', 'BlackBerry (7.1, 7)', '360 x  480', 188, 'TFT', 5.0, null, 0.5, 0.512, 1230, 5.5, 360.0, '2.1, EDR', 'GPS, A-GPS', 'manufacturer/BlackBerry/BlackBerry Curve 9380.jpg', 'BlackBerry Curve 9380 is the first ever Curve with a capacitive touch display. The handset features a 3.2" display with 360x480 pixels, an 800MHz processor and a 5MP camera with flash and VGA video recording. Of course, onboard are BBM and all the usual BlackBerry software security features, that have made RIM''s devices a favorite for enterprises and government agencies.');

insert into phone2color (phoneId, colorId) values (1101, 1000);
insert into phone2color (phoneId, colorId) values (1744, 1000);
insert into phone2color (phoneId, colorId) values (1744, 1009);
insert into phone2color (phoneId, colorId) values (1744, 1006);
insert into phone2color (phoneId, colorId) values (1744, 1008);
insert into phone2color (phoneId, colorId) values (1744, 1003);
insert into phone2color (phoneId, colorId) values (1744, 1001);
insert into phone2color (phoneId, colorId) values (1999, 1000);

insert into stocks (phoneId, stock, reserved) values (1101, 21, 1);
insert into stocks (phoneId, stock, reserved) values (1744, 4, 4);
insert into stocks (phoneId, stock, reserved) values (1999, 19, 8);