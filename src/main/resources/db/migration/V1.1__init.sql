
create table message(
	id int auto_increment not null primary key,
	created datetime,
	to_id varchar(50),
	from_id varchar(20),
	summary varchar(50),
	text varchar(50)
);

insert into message(id,created,to_id,from_id,summary,text) values (100,'2018-09-20 08:00:00','jcummings','jgrandja','Hello Josh','This message is for Josh');
insert into message(id,created,to_id,from_id,summary,text) values (101,'2018-09-20 10:00:00','jcummings','rwinch','How are you Josh?','This message is for Josh');
insert into message(id,created,to_id,from_id,summary,text) values (102,'2018-09-21 14:00:00','jcummings','jgrandja','Is this secure?','This message is for Josh');

insert into message(id,created,to_id,from_id,summary,text) values (200,'2018-09-18 10:00:00','jgrandja','jcummings','Hello Joe','This message is for Joe');
insert into message(id,created,to_id,from_id,summary,text) values (201,'2018-09-20 12:00:00','jgrandja','rwinch','How are you Joe?','This message is for Joe');
insert into message(id,created,to_id,from_id,summary,text) values (202,'2018-09-21 18:00:00','jgrandja','jcummings','Is this secure?','This message is for Joe');

insert into message(id,created,to_id,from_id,summary,text) values (300,'2018-09-19 11:00:00','rwinch','jgrandja','Hello Rob','This message is for Rob');
insert into message(id,created,to_id,from_id,summary,text) values (301,'2018-09-21 13:00:00','rwinch','jcummings','How are you Rob?','This message is for Rob');
insert into message(id,created,to_id,from_id,summary,text) values (302,'2018-09-22 16:00:00','rwinch','jcummings','Is this secure?','This message is for Rob');



