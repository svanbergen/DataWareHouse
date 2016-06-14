drop table bigspenders;
drop table reply;
drop table reservation;
drop table review;
drop table located;
drop table residesin;
drop table location;
drop table postalcode;
drop table includes;
drop table orders;
drop table menuitem;
drop table customer;
drop table business;
drop table businessowner;



CREATE TABLE BusinessOwner (
	ownerUserName varchar(25) Primary Key,
	password varchar(25),
	check (REGEXP_LIKE(ownerUserName,'^[a-zA-Z][a-zA-Z0-9.,$;]*$')));

grant select on BusinessOwner to public;

CREATE TABLE Business	(
	BusinessID number Primary Key,
	Neighborhood varchar(20),
	PhoneNum varchar(20),
	Website varchar(50),
	Type varchar(40),
	ownerUsername varchar(25) not null,
	reservationFlag char(1),
	dayOfOperation varchar(7) not null,
	startTime int not null,
	finishTime int not null,
	Foreign Key(ownerUsername) references BusinessOwner
		On Delete Cascade,
    check (REGEXP_LIKE(PhoneNum,'[0-9][0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]')),
    check (Website like '%.%'),
    check (startTime >= 0 and startTime < 2360),
    check (finishTime >= 0 and finishTime < 2360),
    check (reservationFlag = 'N' or reservationFlag = 'Y'),
    check (REGEXP_LIKE(dayOfOperation, '[MTWRFSU]{1,7}'))
    );

drop sequence BusinessID_seq;
CREATE SEQUENCE BusinessID_seq;
CREATE TRIGGER businessID_trig
 BEFORE INSERT ON Business
 	FOR EACH ROW
		BEGIN
			select BusinessID_seq.nextval
			into :new.BusinessID
			from dual
		END;
		END LOOP;
/

grant select on business to public;

CREATE TABLE Customer (
	customerUsername varchar(25) Primary Key,
	password varchar(25),
	phoneNum varchar(20),
    check (REGEXP_LIKE(PhoneNum,'[0-9][0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]')),
	check (REGEXP_LIKE(customerUsername,'^[A-Za-z][A-Za-z0-9]*$'))
    );


grant select on customer to public;

CREATE TABLE Review (
	ReviewID integer Primary Key,
	Rating int not null,
	Comments varchar(200),
	ReviewDate date not null,
	BusinessID integer not null,
	CustomerUsername varchar(25) not null,
	Foreign Key(BusinessID) references Business
		On delete Cascade,
	Foreign Key(customerUsername) references Customer
		On delete Cascade,
    check (Rating >= 1 and Rating <= 5)
    );

drop sequence ReviewID_seq;
CREATE SEQUENCE ReviewID_seq;
CREATE TRIGGER reviewID_trig
 BEFORE INSERT ON Review
 	FOR EACH ROW
		BEGIN
			select ReviewID_seq.nextval
			into :new.ReviewID
			from dual
		END;
		END LOOP;
/

grant select on review to public;



CREATE TABLE MenuItem (
	menuItemID int Primary Key,
	Price float not null,
	ItemType varchar(15),
	Name varchar(20) not null,
	BusinessID int not null,
	Foreign Key(BusinessID) references Business
		On delete Cascade,
    check (Price > 0));
	
drop sequence MenuItemID_seq;
CREATE SEQUENCE MenuItemID_seq;
CREATE TRIGGER MenuItemID_trig
 BEFORE INSERT ON MenuItem
 	FOR EACH ROW
		BEGIN
			select MenuItemID_seq.nextval
			into :new.menuItemID
			from dual
		END;
		END LOOP;
/

grant select on menuitem to public;

CREATE TABLE Orders (
	OrderID int Primary Key,
	timeMade timestamp,
	Price float,
	BusinessID int not null,
	customerUserName varchar(25) not null,
	Foreign Key(BusinessID) references Business
		On delete Cascade,
	Foreign Key(customerUsername) references Customer
		On delete Cascade,
    check (Price >= 0));
	
drop sequence OrderID_seq;
CREATE SEQUENCE OrderID_seq;
CREATE TRIGGER OrderID_trig
 BEFORE INSERT ON Orders
 	FOR EACH ROW
		BEGIN
			select OrderID_seq.nextval
			into :new.OrderID
			from dual
		END;
		END LOOP;
/

CREATE TABLE BigSpenders (
	CustomerUsername varchar(25),
	orderid int,
	price float,
	businessid int,
	Foreign Key(customerUsername) references Customer,
	Foreign Key(OrderID) references Orders,
	Primary Key(customerUsername,OrderID),
	check (Price > 100)
);

CREATE OR REPLACE TRIGGER spender_trig
 AFTER UPDATE ON Orders
 FOR EACH ROW
 		WHEN (new.price > 100 and new.timeMade is not null)
		BEGIN
			INSERT INTO BigSpenders values(:new.customerUsername, :new.orderid, :new.price, :new.businessid);
		EXCEPTION
  			WHEN DUP_VAL_ON_INDEX
 			THEN ROLLBACK;
		END;
/



grant select on orders to public;


CREATE TABLE Includes (
	orderID int,
	menuItemID int,
	Primary Key (orderID, menuItemID),
	Foreign Key(orderID) references Orders,
	Foreign Key(menuItemID) references MenuItem 
	);

grant select on includes to public;


CREATE OR REPLACE TRIGGER includes_trig
 AFTER INSERT ON Includes
 FOR EACH ROW
 		DECLARE
		    newcost int;
		BEGIN
			SELECT price
			INTO newcost
			FROM MenuItem
			WHERE menuitem.menuItemID = :new.menuitemid;
			
			UPDATE Orders
    		SET    price = price + newcost
   			WHERE  orderid = :new.orderid;
		END;
/

CREATE TABLE PostalCode (
	postalCode varchar(7) Primary Key,
	City varchar(40) not null,
	Province varchar(2),
    check (REGEXP_LIKE(postalCode,'[A-Z][0-9][A-Z] [0-9][A-Z][0-9]'))
    );

grant select on postalcode to public;



CREATE TABLE Location (
	locationID int Primary Key,
	unitNum varchar(6),
	streetAdd varchar(30) not null,
	postalCode varchar(7) not null,
	Foreign Key(PostalCode) references PostalCode );

grant select on location to public;

drop sequence LocationID_seq;
CREATE SEQUENCE LocationID_seq;
CREATE TRIGGER LocationID_trig
 BEFORE INSERT ON Location
 	FOR EACH ROW
		BEGIN
			select LocationID_seq.nextval
			into :new.locationID
			from dual
		END;
		END LOOP;
/


CREATE TABLE Located (
	BusinessID int,
	locationID int,
	Primary Key(BusinessID, locationID),
	Foreign Key(BusinessID) references Business,
	Foreign Key(locationID) references location);


grant select on located to public;


CREATE TABLE ResidesIn (
	locationID int,
	customerUsername varchar(25),
	Primary Key(locationID, customerUsername),
	Foreign Key(locationID) references Location,
	Foreign Key(customerUsername) references Customer );

grant select on residesin to public;

CREATE TABLE Reservation (
	Dates timestamp,
	customerUsername varchar(25) not null,
	businessID int,
	Primary Key(dates, customerUsername, businessID),
	Foreign Key(CustomerUsername) references Customer,
	Foreign Key(BusinessID) references Business );

grant select on reservation to public;

CREATE TABLE Reply (
	ReviewID int,
	OwnerUsername varchar(25),
	ReplyDate date,
	replyContent varchar(200) not null,
	Primary Key (reviewID, OwnerUsername),
	Foreign Key(reviewID) references Review,
	Foreign Key(OwnerUsername) references BusinessOwner );


grant select on reply to public;

insert into BusinessOwner
	values('billsmith1025', '12345');

insert into BusinessOwner
	values('a123amycampbell', '12345');

insert into BusinessOwner
	values('samsonthegreat', '12345');

insert into BusinessOwner
	values('helloitsme123', '12345');

insert into BusinessOwner
	values('ilovefriedchicken', '12345');

insert into Business 
values(102, 'Marpole', '604-903-0293', 'www.billsrestaurant.com', 'Greek restaurant', 'billsmith1025', 'Y', 'MTWRFS',
1000, 2300);

insert into Business 
values(83, 'Dunbar', '604-113-7599', 'www.bestseafood.com', 'Seafood restaurant', 'a123amycampbell', 'N', 'MTWRF',
1000, 2200);

insert into Business 
values(180, 'Whalley', '604-103-0113', 'www.fivestars.com', 'Fine dining restaurant', 'samsonthegreat', 'Y', 'MTWRFSU',
1130, 2330);

insert into Business 
values(45, 'Marpole', '604-903-1193', 'www.yummyfood.com', 'Sushi restaurant', 'helloitsme123', 'Y', 'MTWRFSU',
0800, 2100);

insert into Business 
values(123, 'Boundary', '604-113-0113', 'www.worldsbestfriedchicken.com', 'Fast food joint', 'ilovefriedchicken', 'N', 'MTWRFSU',
0900, 2200);

insert into Customer
	values('henry00123', '12345', '778-710-3645');

insert into Customer
	values('angrytim', '12345', '778-112-3345');

insert into Customer
	values('sarahgibson92', '12345', '778-114-3645');

insert into Customer
	values('joeiscool', '12345', '604-710-3645');

insert into Customer
	values('foodiefoodie', '12345', '604-100-3412');

insert into Review
values(316, 4, 'Delicious food!', TO_DATE('2013/07/27','yyyy/mm/dd'), 1, 'henry00123');

insert into Review
values(420, 1, 'Horrible service. I am never, EVER, returning!', TO_DATE('2015/7/27','yyyy/mm/dd'), 2, 'angrytim');

insert into Review
values(51, 5, 'Absolutely mindblown!', TO_DATE('2012/03/10','yyyy/mm/dd'), 3, 'sarahgibson92');

insert into Review
values(103, 2, 'Mediocre.', TO_DATE('2015/01/14','yyyy/mm/dd'), 4, 'joeiscool');

insert into Review
values(120, 5, 'I cannot get enough of their fried chicken!', TO_DATE('2013/01/17','yyyy/mm/dd'), 5, 'foodiefoodie');

insert into MenuItem
	values(1, 16.00, 'Entree', 'Beef Souvlaki', 1);
	
insert into MenuItem
	values(2, 16.00, 'Drink', 'Coffee', 1);
	
insert into MenuItem
	values(3, 16.00, 'Entree', 'Beef Souvlaki', 3);

insert into MenuItem
	values(3, 30.00, 'Entree', 'Lobster', 3);

	insert into MenuItem
	values(3, 70.00, 'Dessert', 'Souffle', 3);

	insert into MenuItem
	values(2, 16.00, 'Drink', 'Coffee', 3);

insert into MenuItem
	values(4, 8.99, 'Appetizer', 'Seared Scallops', 2);

insert into MenuItem
	values(5, 22.50, 'Entree', 'Prime Rib', 3);

insert into MenuItem
	values(6, 10.00, 'Appetizer', 'Salmon Sashimi', 4);

insert into MenuItem
	values(7, 6.99, 'Entree', 'Fried Chicken', 5);

insert into Orders
	values(1, TO_TIMESTAMP('2010/02/13 09:15:30', 'YYYY/MM/DD HH24:MI:SS'), 0, 1, 'henry00123');

insert into Orders
	values(2, TO_TIMESTAMP('2011/01/23 11:15:33', 'YYYY/MM/DD HH24:MI:SS'), 0, 2, 'angrytim');

insert into Orders
	values(3, TO_TIMESTAMP('2014/12/10 14:30:30', 'YYYY/MM/DD HH24:MI:SS'), 0, 3, 'sarahgibson92');

insert into Orders
	values(4, TO_TIMESTAMP(null, 'YYYY/MM/DD HH24:MI:SS'), 0, 4, 'joeiscool');

insert into Orders
	values(5, TO_TIMESTAMP(null, 'YYYY/MM/DD HH24:MI:SS'), 0, 5, 'foodiefoodie');

insert into Includes 
	values(1, 1);

insert into Includes 
	values(1, 2);

insert into Includes 
	values(3, 3);

insert into Includes 
	values(2, 4);

insert into Includes 
	values(3, 5);

insert into PostalCode
	values('V6R 0X2', 'Vancouver', 'BC');

insert into PostalCode
	values('V6T 1X2', 'Vancouver', 'BC');

insert into PostalCode
	values('V3R 0X2', 'Surrey', 'BC');

insert into PostalCode
	values('V6R 1T2', 'Vancouver', 'BC');

insert into PostalCode
	values('V3J 0R2', 'Burnaby', 'BC');

insert into Location
	values(42, NULL, '123 Ash St', 'V6R 0X2');

insert into Location
	values(13, '87', '145 Willow St', 'V6T 1X2');

insert into Location
	values(3, NULL, '123 Peaches St', 'V3R 0X2');

insert into Location
	values(50, '22', '333 Oak St', 'V6R 1T2');

insert into Location
	values(40, NULL, '123 Wood St', 'V3J 0R2');

insert into Located
	values(1, 1);

insert into Located
	values(2, 2);

insert into Located
	values(3, 3);

insert into Located
	values(4, 4);

insert into Located
	values(5, 5);

insert into ResidesIn
	values(1, 'joeiscool');

insert into ResidesIn
	values(2, 'angrytim');

insert into ResidesIn
	values(3, 'sarahgibson92');

insert into ResidesIn
	values(4, 'foodiefoodie');

insert into ResidesIn
	values(5, 'henry00123');

insert into Reservation
	values(TO_TIMESTAMP('2011/07/27 09:00:00', 'YYYY/MM/DD HH24:MI:SS'), 'henry00123', 1);

insert into Reservation
	values(TO_TIMESTAMP('2014/02/01 19:30:00', 'YYYY/MM/DD HH24:MI:SS'), 'angrytim', 2);

insert into Reservation
	values(TO_TIMESTAMP('2015/04/20 16:15:00', 'YYYY/MM/DD HH24:MI:SS'), 'sarahgibson92', 3);

insert into Reservation
	values(TO_TIMESTAMP('2015/09/10 21:00:00', 'YYYY/MM/DD HH24:MI:SS'), 'joeiscool', 4);

insert into Reservation
	values(TO_TIMESTAMP('2011/07/27 10:00:00', 'YYYY/MM/DD HH24:MI:SS'), 'foodiefoodie', 5);

insert into Reply 
	values(1, 'billsmith1025', TO_DATE('2013/08/01','yyyy/mm/dd'), 'Thank you for the kind words!');

insert into Reply
	values(2, 'a123amycampbell', TO_DATE('2015/07/29','yyyy/mm/dd'), 'Sorry your experience was not ideal. We are working hard to improve our service.');

insert into Reply 
	values(3, 'samsonthegreat', TO_DATE('2014/12/17','yyyy/mm/dd'), 'We are so pleased that you enjoy our food!');

insert into Reply 
	values(4, 'helloitsme123', TO_DATE('2015/01/20','yyyy/mm/dd'), 'Thank you for the review. We are striving to improve.');

insert into Reply 
	values(5, 'ilovefriedchicken', TO_DATE('2013/01/20','yyyy/mm/dd'), 'I am glad you love fried chicken as much as we do!');

commit;





