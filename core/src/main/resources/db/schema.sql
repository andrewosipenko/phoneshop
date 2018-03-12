DROP TABLE IF EXISTS phone2color;
DROP TABLE IF EXISTS colors;
DROP TABLE IF EXISTS stocks;
DROP TABLE IF EXISTS phones;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS orderItems;

CREATE TABLE colors (
  id   BIGINT AUTO_INCREMENT PRIMARY KEY,
  code VARCHAR(50),
  UNIQUE (code)
);

CREATE TABLE phones (
  id                    BIGINT AUTO_INCREMENT PRIMARY KEY,
  brand                 VARCHAR(50)  NOT NULL,
  model                 VARCHAR(254) NOT NULL,
  price                 FLOAT,
  displaySizeInches     FLOAT,
  weightGr              SMALLINT,
  lengthMm              FLOAT,
  widthMm               FLOAT,
  heightMm              FLOAT,
  announced             DATETIME,
  deviceType            VARCHAR(50),
  os                    VARCHAR(100),
  displayResolution     VARCHAR(50),
  pixelDensity          SMALLINT,
  displayTechnology     VARCHAR(50),
  backCameraMegapixels  FLOAT,
  frontCameraMegapixels FLOAT,
  ramGb                 FLOAT,
  internalStorageGb     FLOAT,
  batteryCapacityMah    SMALLINT,
  talkTimeHours         FLOAT,
  standByTimeHours      FLOAT,
  bluetooth             VARCHAR(50),
  positioning           VARCHAR(100),
  imageUrl              VARCHAR(254),
  description           VARCHAR(4096),
  CONSTRAINT UC_phone UNIQUE (brand, model)
);

CREATE TABLE phone2color (
  phoneId BIGINT,
  colorId BIGINT,
  CONSTRAINT FK_phone2color_phoneId FOREIGN KEY (phoneId) REFERENCES phones (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_phone2color_colorId FOREIGN KEY (colorId) REFERENCES colors (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE stocks (
  phoneId  BIGINT   NOT NULL,
  stock    SMALLINT NOT NULL,
  reserved SMALLINT NOT NULL,
  UNIQUE (phoneId),
  CONSTRAINT FK_stocks_phoneId FOREIGN KEY (phoneId) REFERENCES phones (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE orders (
  id              BIGINT AUTO_INCREMENT PRIMARY KEY,
  subtotal        FLOAT,
  deliveryPrice   FLOAT,
  totalPrice      FLOAT,
  firstName       VARCHAR(254) NOT NULL,
  lastName        VARCHAR(254) NOT NULL,
  deliveryAddress VARCHAR(500) NOT NULL,
  contactPhoneNo  VARCHAR(30)  NOT NULL,
  status ENUM('NEW', 'DELIVERED', 'REJECTED')
);

CREATE TABLE orderItems (
  id       BIGINT AUTO_INCREMENT PRIMARY KEY,
  orderId  BIGINT,
  phoneId  BIGINT,
  quantity BIGINT NOT NULL,
  CONSTRAINT FK_orderItem_orderId FOREIGN KEY (orderId) REFERENCES orders (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_orderItem_phoneId FOREIGN KEY (phoneId) REFERENCES phones (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TRIGGER beforeInsertOrderItems
BEFORE INSERT
  ON orderItems
FOR EACH ROW
CALL "com.es.core.trigger.StockCheckOrderItemTrigger";

CREATE TRIGGER beforeUpdateOrderItems
BEFORE UPDATE
  ON orderItems
FOR EACH ROW
CALL "com.es.core.trigger.StockCheckOrderItemTrigger";

CREATE TRIGGER beforeDeleteOrderItems
AFTER DELETE
  ON orderItems
FOR EACH ROW
CALL "com.es.core.trigger.StockCheckOrderItemTrigger";