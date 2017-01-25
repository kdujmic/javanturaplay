# --- First database schema

# --- !Ups

set ignorecase true;

create table company (
  id                        bigint not null,
  name                      varchar(255) not null,
  constraint pk_company primary key (id))
;

create table speaker (
  id                        bigint not null,
  name                      varchar(255) not null,
  session                   varchar(500) not null,
  registrated                timestamp,
  company_id                bigint,
  constraint pk_speaker primary key (id))
;

create sequence company_seq start with 1000;

create sequence speaker_seq start with 1000;

alter table speaker add constraint fk_speaker_company_1 foreign key (company_id) references company (id) on delete restrict on update restrict;
create index ix_speaker_company_1 on speaker (company_id);


# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists company;

drop table if exists speaker;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists company_seq;

drop sequence if exists speaker_seq;

