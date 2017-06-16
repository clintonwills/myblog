# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table comment (
  id                        bigint auto_increment not null,
  content                   TEXT,
  created                   datetime,
  post_id                   bigint,
  constraint pk_comment primary key (id))
;

create table post (
  id                        bigint auto_increment not null,
  subject                   varchar(255),
  content                   TEXT,
  created                   datetime,
  created_by_id             bigint,
  constraint pk_post primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  username                  varchar(255),
  password                  varchar(255),
  constraint pk_user primary key (id))
;

alter table comment add constraint fk_comment_post_1 foreign key (post_id) references post (id) on delete restrict on update restrict;
create index ix_comment_post_1 on comment (post_id);
alter table post add constraint fk_post_createdBy_2 foreign key (created_by_id) references user (id) on delete restrict on update restrict;
create index ix_post_createdBy_2 on post (created_by_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table comment;

drop table post;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

