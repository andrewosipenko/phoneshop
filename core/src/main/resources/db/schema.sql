drop table if exists phone2color;
drop table if exists colors;
drop table if exists stocks;
drop table if exists phones;
drop table if exists orders;
drop table if exists orderItems;
drop table if exists order2orderItem;
drop table if exists orderId2orderKey;
drop table if exists comments;

create table colors (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  code VARCHAR(50),
  UNIQUE (code)
);

create table phones (
  id BIGINT AUTO_INCREMENT primary key,
  brand VARCHAR(50) NOT NULL,
  model VARCHAR(254) NOT NULL,
  price FLOAT,
  displaySizeInches FLOAT,
  weightGr SMALLINT,
  lengthMm FLOAT,
  widthMm FLOAT,
  heightMm FLOAT,
  announced DATETIME,
  deviceType VARCHAR(50),
  os VARCHAR(100),
  displayResolution VARCHAR(50),
  pixelDensity SMALLINT,
  displayTechnology VARCHAR(50),
  backCameraMegapixels FLOAT,
  frontCameraMegapixels FLOAT,
  ramGb FLOAT,
  internalStorageGb FLOAT,
  batteryCapacityMah SMALLINT,
  talkTimeHours FLOAT,
  standByTimeHours FLOAT,
  bluetooth VARCHAR(50),
  positioning VARCHAR(100),
  imageUrl VARCHAR(254),
  description VARCHAR(4096),
  CONSTRAINT UC_phone UNIQUE (brand, model)
);

create table phone2color (
  phoneId BIGINT,
  colorId BIGINT,
  CONSTRAINT FK_phone2color_phoneId FOREIGN KEY (phoneId) REFERENCES phones (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_phone2color_colorId FOREIGN KEY (colorId) REFERENCES colors (id) ON DELETE CASCADE ON UPDATE CASCADE
);

create table stocks (
  phoneId BIGINT NOT NULL,
  stock SMALLINT NOT NULL,
  reserved SMALLINT NOT NULL,
  UNIQUE (phoneId),
  CONSTRAINT FK_stocks_phoneId FOREIGN KEY (phoneId) REFERENCES phones (id) ON DELETE CASCADE ON UPDATE CASCADE
);

create table orders (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  subtotal FLOAT,
  deliveryPrice FLOAT,
  totalPrice FLOAT,
  firstName VARCHAR(50) NOT NULL,
  lastName VARCHAR(50) NOT NULL,
  deliveryAddress VARCHAR(100) NOT NULL,
  contactPhoneNo VARCHAR(20) NOT NULL,
  additionalInformation VARCHAR(4096),
  status VARCHAR(50),
  UNIQUE (id)
);

create table orderItems(
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  phoneId BIGINT,
  orderId BIGINT,
  quantity BIGINT,
  UNIQUE(id),
  CONSTRAINT FK_orderItems_orderId FOREIGN KEY (orderId) REFERENCES orders (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_orderItems_phoneId FOREIGN KEY (phoneId) REFERENCES phones (id) ON DELETE CASCADE ON UPDATE CASCADE
);

create table order2orderItem (
  orderId BIGINT,
  orderItemId BIGINT,
  CONSTRAINT FK_order2orderItem_orderId FOREIGN KEY (orderId) REFERENCES orders (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_order2orderItem_orderItemId FOREIGN KEY (orderItemId) REFERENCES orderItems (id) ON DELETE CASCADE ON UPDATE CASCADE
);

create table orderId2orderKey (
  orderId BIGINT NOT NULL,
  orderKey VARCHAR(50) NOT NULL,
  UNIQUE (orderId),
  CONSTRAINT FK_orderId2orderKey_orderId FOREIGN KEY (orderId) REFERENCES orders (id) ON DELETE CASCADE  ON UPDATE CASCADE
);

create table comments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    phoneId BIGINT,
    authorName VARCHAR(50) NOT NULL,
    rating SMALLINT NOT NULL,
    commentText VARCHAR(4096) NOT NULL,
    status VARCHAR(50),
    CONSTRAINT FK_comments_phoneId FOREIGN KEY (phoneId) REFERENCES phones (id) ON DELETE CASCADE ON UPDATE CASCADE
);