# --- Sample dataset

# --- !Ups

insert into company (id,name) values (  1,'Etna d.o.o');
insert into company (id,name) values (  2,'ArangoDB GmbH');
insert into company (id,name) values (  3,'University of Ljubljana');
insert into company (id,name) values (  4,'NEOS');
insert into company (id,name) values (  5,'Liferay');
insert into company (id,name) values (  6,'King ICT');
insert into company (id,name) values (  7,'Croz');


insert into speaker (id,name,session,registrated,company_id) values (  1,'Kresimir Dujmic','Java or Scala – Web development with Playframework 2.5.x?','1991-01-01',1);
insert into speaker (id,name,session,registrated,company_id) values (  2,'Michael Hackstein','Handling Billions of Edges in a Graph Database','2017-01-01',2);
insert into speaker (id,name,session,registrated,company_id) values (  3,'Matjaž B. Jurič','Cloud-native Architectures and Java','2016-01-01',3);
insert into speaker (id,name,session,registrated,company_id) values (  4,'Matija Dujmović','True RESTful Java web services with Elide and Katharsis','2016-12-06',4);
insert into speaker (id,name,session,registrated,company_id) values (  5,'Milen Dyankov','What’s NOT new in modular Java?','2017-01-01',5);
insert into speaker (id,name,session,registrated,company_id) values (  6,'Aleksander Radovan','Java lambdas and stream – are they better than for loops?','2017-01-01',6);
insert into speaker (id,name,session,registrated,company_id) values (  7,'Ivan Krnić','The Power of the Cloud in a professional services company','2017-01-01',7);
insert into speaker (id,name,session,registrated,company_id) values (  8,'Danijel Mitar','Test-driven documentation with Spring REST Docs','2017-01-01',6);
insert into speaker (id,name,session,registrated,company_id) values (  9,'Josip Kovaček','Spring Boot and JavaFX – can they play together?','2017-01-01',6);

# --- !Downs

delete from speaker;
delete from company;
