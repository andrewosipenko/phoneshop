drop table if exists phone2color;
drop table if exists colors;
drop table if exists stocks;
drop table if exists phones;
drop table if exists orders;
drop table if exists order2phone;
drop table if exists users;
drop table if exists user_roles;

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
  firstName VARCHAR(50) NOT NULL,
  lastName VARCHAR(50) NOT NULL,
  deliveryAddress VARCHAR(100) NOT NULL,
  contactPhoneNo VARCHAR(50) NOT NULL,
  additionInfo VARCHAR(4000) NOT NULL,
  date DATETIME NOT NULL,
  subtotal FLOAT NOT NULL,
  deliveryPrice FLOAT NOT NULL,
  total FLOAT NOT NULL,
  status VARCHAR(20) NOT NULL
);

create table order2phone (
  orderId BIGINT,
  phoneId BIGINT,
  quantity INT NOT NULL,
  FOREIGN KEY (orderId) REFERENCES orders (id),
  FOREIGN KEY (phoneId) REFERENCES phones (id)
);

create table users (
  username VARCHAR(30) NOT NULL,
  password VARCHAR(30) NOT NULL,
  enabled INT NOT NULL DEFAULT 1,
  PRIMARY KEY (username)
);

create table user_roles (
  userRoleId BIGINT NOT NULL AUTO_INCREMENT,
  username VARCHAR(30) NOT NULL,
  role VARCHAR(30) NOT NULL,
  PRIMARY KEY (userRoleId),
  FOREIGN KEY (username) REFERENCES users(username)
);