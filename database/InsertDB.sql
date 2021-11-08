INSERT INTO Subscriptions VALUES (1, 'Golden','With a Golden subscription, you have an opportunity to add three companies for tracking and receiving data on shares of specific companies for 52 weeks and financial statements of companies.', 90.00, 3);
INSERT INTO Subscriptions VALUES (2, 'Silver','With a Silver subscription, you have an opportunity to add three companies for tracking and get 52 weeks of stock data for specific companies.', 60.00, 3);
INSERT INTO Subscriptions VALUES (3, 'Bronze','With a Bronze subscription, you have an opportunity to add two companies for tracking', 30.00, 2);

INSERT INTO Users VALUES (1, 'Admin' , 'Adminovich','admin@mail.com' , '2021-08-23', '2021-09-16', 'ACTIVE', '$2a$12$XmFno4d.H65Xo6hdwIiwqepfBjkq9e86HTKnG5LLlF4Z8RPbRIY5K');
INSERT INTO Users VALUES (2, 'User' ,'Userovich' ,'user@mail.com' , '2021-09-01', '2021-11-16', 'ACTIVE', '$2a$12$uZZIRrElg9H/gdd3nx/52u8mx2Y5qaGHWVXHWb6LVjNPXDe1d5xdm');
INSERT INTO Users VALUES (3, 'Anna' ,'Ivanova' ,'anna@mail.com' , '2021-09-02', '2021-09-16', 'CREATED', '$2a$12$jHxrU5xPBg0JT9EmYbQuce12SfmXav.071MXYy1KgR4mOiPIkA8Yq');

INSERT INTO Users_Subscriptions VALUES (1, 1, 1,'2021-09-02','2021-11-16','ACTIVE');
INSERT INTO Users_Subscriptions VALUES (2, 2, 2,'2021-09-02','2021-11-16','INACTIVE');
INSERT INTO Users_Subscriptions VALUES (3, 3, 3,'2021-09-02','2021-11-16','EXPIRED');

INSERT INTO Roles VALUES (1, 'ADMIN');
INSERT INTO Roles VALUES (2, 'USER');

INSERT INTO Users_Roles VALUES (1, 1);
INSERT INTO Users_Roles VALUES (2, 2);
INSERT INTO Users_Roles VALUES (3, 2);