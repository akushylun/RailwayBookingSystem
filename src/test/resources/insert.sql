INSERT INTO `login` VALUES (1,'mark123@gmail.com','mark1990');
INSERT INTO `login` VALUES (2,'bob123@ukr.net','12345');

INSERT INTO `person` VALUES (1,'mark','johnson', 1,'USER');
INSERT INTO `person` VALUES (2,'bob','smith', 1,'ADMIN');

INSERT INTO `ticket` VALUES (1,NULL);
INSERT INTO `ticket` VALUES (2,NULL);

INSERT INTO `booking` VALUES (1,100.00,'2017-07-27 19:58:27',1,1);
INSERT INTO `booking` VALUES (2,300.00,'2018-03-01 16:20:24',1,2);

INSERT INTO `station` VALUES (1,'kiev');
INSERT INTO `station` VALUES (2,'odessa');
INSERT INTO `station` VALUES (3,'lviv');
INSERT INTO `station` VALUES (4,'rivne');

INSERT INTO `train` VALUES (1,'intercity');
INSERT INTO `train` VALUES (2,'hyundai');

INSERT INTO `departure` VALUES (1, '2017-07-08 09:38:15', 1);
INSERT INTO `departure` VALUES (2, '2017-06-04 09:38:15', 2);



INSERT INTO `m2m_train_station` VALUES (1,0, 0.00, 1,1);
INSERT INTO `m2m_train_station` VALUES (2,0, 0.00, 2,2);
INSERT INTO `m2m_train_station` VALUES (3,30, 50.40, 3,1);
INSERT INTO `m2m_train_station` VALUES (4,60, 190.99, 4,2);

INSERT INTO `m2m_ticket_train_station`  VALUES (1, 1);
INSERT INTO `m2m_ticket_train_station`  VALUES (1, 2);
INSERT INTO `m2m_ticket_train_station`  VALUES (2, 3);
INSERT INTO `m2m_ticket_train_station`  VALUES (2, 4);