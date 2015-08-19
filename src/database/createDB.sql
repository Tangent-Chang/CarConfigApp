CREATE DATABASE CarConfigApp;
CREATE USER 'car_user'@'localhost' IDENTIFIED BY 'car_pw';
GRANT ALL ON CarConfigApp.* TO 'car_user'@'localhost';

CREATE TABLE IF NOT EXISTS Automobiles
(
  auto_id INT AUTO_INCREMENT,
  maker VARCHAR (128) NOT NULL,
  model VARCHAR (128) NOT NULL,
  baseprice DOUBLE NOT NULL,
  PRIMARY KEY (auto_id)
);
CREATE TABLE IF NOT EXISTS Optionsets
(
  set_id INT AUTO_INCREMENT,
  auto_id INT NOT NULL,
  set_name VARCHAR (128) NOT NULL,
  PRIMARY KEY (set_id),
  FOREIGN KEY (auto_id) REFERENCES Automobiles(auto_id)
);
CREATE TABLE IF NOT EXISTS Options
(
  option_id INT AUTO_INCREMENT,
  set_id INT NOT NULL,
  option_name VARCHAR (128) NOT NULL,
  option_price DOUBLE NOT NULL,
  PRIMARY KEY (option_id),
  FOREIGN KEY (set_id) REFERENCES Optionsets(set_id)
);