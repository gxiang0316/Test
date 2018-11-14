create table user (
	id integer(10),
	name varchar(20),
	course varchar(10),
	score double(4,2)

);

insert into user (id,name,course,score) values (123,'小明','语文',78.4);
insert into user (id,name,course,score) values (123,'小明','数学',85);
insert into user (id,name,course,score) values (123,'小明','英语',96);
insert into user (id,name,course,score) values (123,'小明','历史',84);

insert into user (id,name,course,score) values (1234,'小丽','语文',92);
insert into user (id,name,course,score) values (1234,'小丽','数学',68);
insert into user (id,name,course,score) values (1234,'小丽','英语',86);
insert into user (id,name,course,score) values (1234,'小丽','历史',78);

select * from user;
