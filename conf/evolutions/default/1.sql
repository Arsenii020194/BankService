# --- !Ups
create table bank (
  id_bank                       bigint not null,
  full_name                     varchar(255),
  bik                           integer,
  constraint pk_bank primary key (id_bank)
);

create sequence bank_seq;

create table bill (
  id_bill                       bigint not null,
  date                          timestamp,
  active_for                    timestamp,
  uslugs                        clob,
  num                           integer,
  order_number                  integer,
  reciever                      integer,
  customer                      varchar(255),
  file                          blob,
  constraint pk_bill primary key (id_bill)
);
create sequence bill_seq;

create table users (
  id_user                       integer not null,
  login                         varchar(255),
  password                      varchar(255),
  constraint pk_users primary key (id_user)
);
create sequence users_seq;

create table user_data (
  id_user_data                  bigint not null,
  user                          integer,
  bill_prolongation             integer,
  inn                           integer,
  bik                           integer,
  kpp                           integer,
  full_name                     varchar(255),
  bank                          bigint,
  adress                        varchar(255),
  phone                         varchar(255),
  constraint uq_user_data_user unique (user),
  constraint uq_user_data_bank unique (bank),
  constraint pk_user_data primary key (id_user_data)
);
create sequence user_data_seq;

alter table bill add constraint fk_bill_reciever foreign key (reciever) references users (id_user) on delete restrict on update restrict;
create index ix_bill_reciever on bill (reciever);

alter table user_data add constraint fk_user_data_user foreign key (user) references users (id_user) on delete restrict on update restrict;

alter table user_data add constraint fk_user_data_bank foreign key (bank) references bank (id_bank) on delete restrict on update restrict;

INSERT INTO USERS VALUES(1, 'test_user', 'test_user');
INSERT INTO bank VALUES(1, 'Тестовый банк', 123564987);
INSERT INTO user_data VALUES(1, 1, 10, 123456789, 987654321, 999999999, 'ооо Тестовая организация', 1, 'г.Тестовый, ул.Тестовая д.тестовый 443099','8937000000');

# --- !Downs

alter table bill drop constraint if exists fk_bill_reciever;
drop index if exists ix_bill_reciever;

alter table user_data drop constraint if exists fk_user_data_user;

alter table user_data drop constraint if exists fk_user_data_bank;

drop table if exists bank;
drop sequence if exists bank_seq;

drop table if exists bill;
drop sequence if exists bill_seq;

drop table if exists users;
drop sequence if exists users_seq;

drop table if exists user_data;
drop sequence if exists user_data_seq;

