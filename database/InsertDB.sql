INSERT INTO Subscriptions (id, name, description, price, tracking_size) VALUES (1, 'Golden','With a Golden subscription, you have an opportunity to add three companies for tracking and receiving data on shares of specific companies for 52 weeks and financial statements of companies.', 90.00, 3);
INSERT INTO Subscriptions (id, name, description, price, tracking_size) VALUES (2, 'Silver','With a Silver subscription, you have an opportunity to add three companies for tracking and get 52 weeks of stock data for specific companies.', 60.00, 3);
INSERT INTO Subscriptions  (id, name, description, price, tracking_size)VALUES (3, 'Bronze','With a Bronze subscription, you have an opportunity to add two companies for tracking', 30.00, 2);

INSERT INTO Roles (id, role_name) VALUES (1, 'ADMIN');
INSERT INTO Roles (id, role_name) VALUES (2, 'USER');

INSERT INTO Users(id, first_name, last_name, email, date_created, date_updated, status, password) VALUES (1, 'Admin' , 'Adminovich','admin@mail.com' , '2021-08-23', '2021-09-16', 'ACTIVE', '$2a$12$XmFno4d.H65Xo6hdwIiwqepfBjkq9e86HTKnG5LLlF4Z8RPbRIY5K');
INSERT INTO Users (id, first_name, last_name, email, date_created, date_updated, status, password) VALUES (2, 'User' ,'Userovich' ,'user@mail.com' , '2021-09-01', '2021-11-16', 'ACTIVE', '$2a$12$uZZIRrElg9H/gdd3nx/52u8mx2Y5qaGHWVXHWb6LVjNPXDe1d5xdm');
INSERT INTO Users (id, first_name, last_name, email, date_created, date_updated, status, password) VALUES (3, 'Anna' ,'Ivanova' ,'anna@mail.com' , '2021-09-02', '2021-09-16', 'CREATED', '$2a$12$jHxrU5xPBg0JT9EmYbQuce12SfmXav.071MXYy1KgR4mOiPIkA8Yq');

INSERT INTO Users_Subscriptions (id, users_id, subscriptions_id, date_start, date_end, sub_status) VALUES (1, 1, 1,'2021-11-16','2021-12-16','ACTIVE');
INSERT INTO Users_Subscriptions (id, users_id, subscriptions_id, date_start, date_end, sub_status) VALUES (2, 2, 2,'2021-11-02','2021-12-02','ACTIVE');
INSERT INTO Users_Subscriptions (id, users_id, subscriptions_id, date_start, date_end, sub_status) VALUES (3, 3, 3,null, null,'INACTIVE');

INSERT INTO Users_Roles (users_id, roles_id) VALUES (1, 1);
INSERT INTO Users_Roles (users_id, roles_id) VALUES (2, 2);
INSERT INTO Users_Roles (users_id, roles_id) VALUES (3, 2);