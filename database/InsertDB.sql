
INSERT INTO Subscriptions VALUES (1, 'Golden','2021-09-02','2021-11-16');
INSERT INTO Subscriptions VALUES (2, 'Silver','2021-09-02','2021-11-16');
INSERT INTO Subscriptions VALUES (3, 'Bronze','2021-09-02','2021-11-16');


INSERT INTO Users VALUES (1, 1,'Admin' , 'Adminovich','admin@mail.com' , '2021-08-23', '2021-09-16', 'ACTIVE', '$2a$12$XmFno4d.H65Xo6hdwIiwqepfBjkq9e86HTKnG5LLlF4Z8RPbRIY5K');
INSERT INTO Users VALUES (2, 3,'User' ,'Userovich' ,'user@mail.com' , '2021-09-01', '2021-09-16', 'ACTIVE', '$2a$12$uZZIRrElg9H/gdd3nx/52u8mx2Y5qaGHWVXHWb6LVjNPXDe1d5xdm');
INSERT INTO Users VALUES (3, 2,'Anna' ,'Ivanova' ,'anna@mail.com' , '2021-09-02', '2021-09-16', 'CREATED', '$2a$12$jHxrU5xPBg0JT9EmYbQuce12SfmXav.071MXYy1KgR4mOiPIkA8Yq');


INSERT INTO Roles VALUES (1, 'ADMIN');
INSERT INTO Roles VALUES (2, 'USER');

INSERT INTO Users_Roles VALUES (1, 1);
INSERT INTO Users_Roles VALUES (2, 2);
INSERT INTO Users_Roles VALUES (3, 2);