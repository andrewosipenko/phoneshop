insert into colors (id, code)
values (1000, 'Black'),
       (1001, 'White'),
       (1002, 'Yellow'),
       (1003, 'Blue'),
       (1004, 'Red'),
       (1005, 'Purple'),
       (1006, 'Gray'),
       (1007, 'Green'),
       (1008, 'Pink'),
       (1009, 'Gold'),
       (1010, 'Silver'),
       (1011, 'Orange'),
       (1012, 'Brown'),
       (1013, '256');

insert into phones (id, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, heightMm, announced,
                    deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels,
                    frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours,
                    standByTimeHours, bluetooth, positioning, imageUrl, description)
values ('1000', 'ARCHOS', 'ARCHOS 101 G9', null, 10.1, 482, 276.0, 167.0, 12.6, null, 'Tablet', 'Android (4.0)',
        '1280 x  800', 149, null, null, 1.3, null, 8.0, null, null, null, '2.1, EDR', 'GPS',
        'manufacturer/ARCHOS/ARCHOS 101 G9.jpg',
        'The ARCHOS 101 G9 is a 10.1'''' tablet, equipped with Google''s open source OS. It offers a multi-core ARM CORTEX A9 processor at 1GHz, 8 or 16GB internal memory, microSD card slot, GPS, Wi-Fi, Bluetooth 2.1, and more.'),
       ('1001', 'ARCHOS', 'ARCHOS 101 Helium 4G', null, 10.1, 482, 250.0, 174.0, 10.0, null, 'Tablet', 'Android (4.4)',
        '1280 x  800', 149, 'IPS LCD', 5.0, 0.3, 1.0, 8.0, 6500, null, null, 'Yes', 'GPS, A-GPS',
        'manufacturer/ARCHOS/ARCHOS 101 Helium 4G.jpg',
        'The ARCHOS 101 Helium 4G is a 10.1" tablet, running Android 4.4. Its specs include 1.5 GHz quad core MediaTek processor, 1 GB RAM, 8 GB storage space, further expandable via microSD card slot, 4G LTE and 2 cameras - 5 MP on the back, and 0.3 MP on the front.'),
       ('1002', 'ARCHOS', 'ARCHOS 101 Green', null, 10.1, 482, null, null, null, null, 'Tablet',
        'Android (2.2)', '1024 x  600', 118, 'TFT', null, 0.3, null, 8.0, null, null, null, '2.1, EDR', null,
        'manufacturer/ARCHOS/ARCHOS 101 Internet Tablet.jpg',
        'Archos 101 Internet Tablet is 10.1" Android running slate with Wi-Fi connectivity. It has 1GHz processor, 0.3MP front camera, HDMI port, 8GB internal memory and supports up to 32GB microSD memory cards.'),
       ('1003', 'ARCHOS', 'ARCHOS 101 Oxygen', 249, 10.1, 482, 240.0, 172.0, 10.0, null, 'Tablet', 'Android (4.4.4)',
        '1200 x  1920', 224, 'IPS LCD', 5.0, 2.0, 2.0, 16.0, 7000, null, null, 'Yes', 'GPS, A-GPS',
        'manufacturer/ARCHOS/ARCHOS 101 Oxygen.jpg',
        'The Archos 101 Oxygen is a tablet that offers a 10.1-inch 1080p display, 1.5 GB of RAM, 16 GB of internal memory, and a quad-core processor.'),
       ('1004', 'ARCHOS', 'ARCHOS 101 Saphir', null, 10.1, 482, 265.4, 181.0, 13.4, null, 'Tablet', 'Android (7.0)',
        '1280 x  800', 149, 'IPS LCD', 5.0, 2.0, 1.0, 16.0, 6000, null, null, 'Yes', 'GPS, A-GPS',
        'manufacturer/ARCHOS/ARCHOS 101 Saphir.jpg',
        'The front of the Saphir 101 is a 10.1-inch IPS display, and while the resolution of 1280 x 800 isn''t the sharpest around, it should be perfectly serviceable for browsing the web, typing out Word documents, watching videos, etc. Android 7.0 Nougat is powering the 101 right out of the box, and the MediaTek MT8163 CPU and 1GB of RAM should provide enough power as long as you aren''t doing anything too intensive on the machine.'),
       ('1005', 'ARCHOS', 'ARCHOS 101 Titanium', null, 10.1, 482, 263.0, 174.0, 10.5, '2013-01-10 00:00:00', 'Tablet',
        'Android (4.1)', '1280 x  800', 149, 'IPS LCD', 2.0, null, 1.0, 8.0, null, null, null, 'Yes', null,
        'manufacturer/ARCHOS/ARCHOS 101 Titanium.jpg',
        'The ARCHOS 101 Titanium offers a 10.1'''' High Definition IPS display, Dual core CPU at 1.6 GHz with Quad core GPU, with 1080p video decoding, Sleek aluminum design and Android 4.1 Jelly Bean with the 700,000 applications on Google Play and the Archos Media Center applications.'),
       ('1006', 'ARCHOS', 'ARCHOS 101 XS 2', 270, 10.1, 482, 273.0, 169.0, 10.1, '2013-11-06 00:00:00', 'Tablet',
        'Android (4.2)', '1280 x  800', 149, 'IPS LCD', 2.0, 2.0, 2.0, 16.0, null, null, null, 'Yes', 'GPS, A-GPS',
        'manufacturer/ARCHOS/ARCHOS 101 XS 2.jpg',
        'The Archos 101 XS 2 can be used as a stand-alone tablet, but it comes bundled with a hardware keyboard, in which it can be docked. The tablet comes with a quad-core 1.6GHz processor and 2GB of RAM, 16 gigs of storage, 10.1'''' display with 1280 x 800 px resolution, and Android 4.2 out of the box.'),
       ('1007', 'ARCHOS', 'ARCHOS 101 XS', null, 10.1, 482, 273.0, 170.0, 8.0, null, 'Tablet', 'Android (4.0.3)',
        '1280 x  800', 149, null, null, null, 1.0, 16.0, null, null, null, '4.0', 'GPS',
        'manufacturer/ARCHOS/ARCHOS 101 XS.jpg',
        'Archos 101 XS tabletis the first of manufacturer''s new XS (extra slim) lineup. Archos has put its focus on design and brings a really slim and well looking device with the 101 XS - at 0.31 inches (7.8mm) it’s actually 15% thinner than the new iPad. The weight is 21 ounces, or 600 grams, also lighter than Apple’s tablet. But it seems that the Android-based Archos 101 XS is taking more on the Asus Transformer series than on the iPad. It comes with a brilliantly designed “magnetic coverboard.” This basically is a very slim and light keyboard that doubles as a cover. With it, the Archos 101 XS is still not too thick at 0.51 inches. Slide it to separate it from the tablet and you can use it as a keyboard dock and stand for the tablet. The rest of the specs match the mid of the road tablet nowadays, but can’t compete with high-end kickers like the iPad and say the Transformer Pad Infinity. You get a 10.1-inch 1280x800-pixel display, a dual-core Cortex-A9 processor clocked at 1.5GHz and with PowerVR SGX544 graphics, and 1GB of RAM.'),
       ('1008', 'ARCHOS', 'ARCHOS 101 neon', null, 10.1, 482, 272.0, 161.0, 12.1, null, 'Tablet', 'Android (4.2)',
        '1024 x  600', 118, null, null, null, 1.0, 8.0, null, null, null, 'Yes', 'GPS, A-GPS',
        'manufacturer/ARCHOS/ARCHOS 101 neon.jpg',
        'The ARCHOS 101 neon is a 10.1-inch tablet with a 1.4 quad-core, 1GB of RAM, 8GB of storage, HDMI and front facing camera.'),
       ('1009', 'ARCHOS', 'ARCHOS 28 Internet Tablet', null, 2.8, 482, null, null, null, null, 'Tablet',
        'Android (2.2)', '240 x  320', 143, 'TFT', null, null, null, 4.0, null, null, null, null, null,
        'manufacturer/ARCHOS/ARCHOS 28 Internet Tablet.jpg',
        'Archos 28 Internet Tablet is a 2.8" Android 2.2 running slate with Wi-Fi connectivity. It has 800MHz processor, 4GB internal memory, 3.5 mm headset jack, accelerometer, and music player.'),
       ('1010', 'ARCHOS', 'ARCHOS 32 Internet Tablet', null, 3.2, 482, 105.0, 55.0, 9.0, null, 'Tablet',
        'Android (2.2)', '240 x  400', 146, 'TFT', 0.3, null, null, 8.0, null, null, null, '2.1, EDR', null,
        'manufacturer/ARCHOS/ARCHOS 32 Internet Tablet.jpg',
        'Archos 32 Internet Tablet is a 3.2" Android 2.2 running slate with VGA camera. It has 800MHz processor, Wi-Fi, 8GB internal memory and Bluetooth.');

insert into phone2color (phoneId, colorId)
values (1000, 1000),
       (1000, 1002),
       (1000, 1001),
       (1001, 1001),
       (1002, 1000),
       (1006, 1001),
       (1009, 1000),
       (1010, 1000);
insert into stocks (phoneId, stock, reserved)
values (1001, 11, 0),
       (1002, 12, 1),
       (1003, 13, 2),
       (1004, 14, 3),
       (1005, 15, 4),
       (1006, 16, 5),
       (1007, 17, 6),
       (1008, 18, 7),
       (1009, 19, 8),
       (1010, 0, 0);
