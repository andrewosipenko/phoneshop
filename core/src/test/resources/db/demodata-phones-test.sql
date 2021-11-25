insert into colors (id, code)
values (1, 'Black');
insert into colors (id, code)
values (2, 'White');
insert into colors (id, code)
values (3, 'Yellow');
insert into colors (id, code)
values (4, 'Red');

insert into phones (id, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, heightMm, announced,
                    deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels,
                    frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours,
                    standByTimeHours, bluetooth, positioning, imageUrl, description)
values ('101', 'ARCHOS', 'ARCHOS 101 G9', 100, 10.1, 482, 276.0, 167.0, 12.6, null, 'Tablet', 'Android (4.0)',
        '1280 x  800', 149, null, null, 1.3, null, 8.0, null, null, null, '2.1, EDR', 'GPS',
        'manufacturer/ARCHOS/ARCHOS 101 G9.jpg',
        'The ARCHOS 101 G9 is a 10.1'''' tablet, equipped with Google''s open source OS. It offers a multi-core ARM CORTEX A9 processor at 1GHz, 8 or 16GB internal memory, microSD card slot, GPS, Wi-Fi, Bluetooth 2.1, and more.');
insert into phones (id, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, heightMm, announced,
                    deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels,
                    frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours,
                    standByTimeHours, bluetooth, positioning, imageUrl, description)
values ('102', 'ARCHOS', 'ARCHOS 101 Helium 4G', 100, 10.1, 482, 250.0, 174.0, 10.0, null, 'Tablet', 'Android (4.4)',
        '1280 x  800', 149, 'IPS LCD', 5.0, 0.3, 1.0, 8.0, 6500, null, null, 'Yes', 'GPS, A-GPS',
        'manufacturer/ARCHOS/ARCHOS 101 Helium 4G.jpg',
        'The ARCHOS 101 Helium 4G is a 10.1" tablet, running Android 4.4. Its specs include 1.5 GHz quad core MediaTek processor, 1 GB RAM, 8 GB storage space, further expandable via microSD card slot, 4G LTE and 2 cameras - 5 MP on the back, and 0.3 MP on the front.');

insert into phone2color (phoneId, colorId)
values (101, 2);
insert into phone2color (phoneId, colorId)
values (102, 4);

insert into stocks (phoneId, stock, reserved)
values (101, 15, 8);
insert into stocks (phoneId, stock, reserved)
values (102, 16, 9);