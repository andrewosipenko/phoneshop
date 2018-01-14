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

insert into phones (id, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, description) values ('1000', 'ARCHOS', 'ARCHOS 101 G9', null, 10.1, 482, 276.0, 167.0, 12.6, null, 'Tablet', 'Android (4.0)', '1280 x  800', 149, null, null, 1.3, null, 8.0, null, null, null, '2.1, EDR', 'GPS', 'manufacturer/ARCHOS/ARCHOS 101 G9.jpg', 'The ARCHOS 101 G9 is a 10.1'''' tablet, equipped with Google''s open source OS. It offers a multi-core ARM CORTEX A9 processor at 1GHz, 8 or 16GB internal memory, microSD card slot, GPS, Wi-Fi, Bluetooth 2.1, and more.');
insert into phones (id, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, description) values ('1001', 'ARCHOS', 'ARCHOS 101 Helium 4G', null, 10.1, 482, 250.0, 174.0, 10.0, null, 'Tablet', 'Android (4.4)', '1280 x  800', 149, 'IPS LCD', 5.0, 0.3, 1.0, 8.0, 6500, null, null, 'Yes', 'GPS, A-GPS', 'manufacturer/ARCHOS/ARCHOS 101 Helium 4G.jpg', 'The ARCHOS 101 Helium 4G is a 10.1" tablet, running Android 4.4. Its specs include 1.5 GHz quad core MediaTek processor, 1 GB RAM, 8 GB storage space, further expandable via microSD card slot, 4G LTE and 2 cameras - 5 MP on the back, and 0.3 MP on the front.');
insert into phones (id, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, description) values ('1002', 'Samsung', 'Samsung Hennessy', null, 3.3, 156, 110.85, 59.3, 18.62, null, 'Smart phone', 'Android (4.1)', '320 x  480', 175, null, 5.0, null, null, null, 1500, null, null, 'Yes', 'GPS, A-GPS', 'manufacturer/Samsung/Samsung Hennessy.jpg', 'The dual-screened Android flip phone called the Samsung Hennessy is stuffed with Android 4.1, which means that the dual 3.3 inch screens with 320 x 480 resolution are responsive thanks to Project Butter. A quad-core 1.2GHz CPU is driving the unit while a 1500mAh battery keeps the lights on. There is a microSD slot on board along with dual SIM slots.');

insert into phone2color (phoneId, colorId) values (1001, 1010);
insert into phone2color (phoneId, colorId) values (1002, 1000);
insert into phone2color (phoneId, colorId) values (1002, 1002);
insert into phone2color (phoneId, colorId) values (1002, 1003);
insert into phone2color (phoneId, colorId) values (1002, 1004);
insert into phone2color (phoneId, colorId) values (1002, 1005);
insert into phone2color (phoneId, colorId) values (1002, 1006);
insert into phone2color (phoneId, colorId) values (1002, 1007);
insert into phone2color (phoneId, colorId) values (1002, 1008);
insert into phone2color (phoneId, colorId) values (1002, 1009);
insert into phone2color (phoneId, colorId) values (1002, 1010);