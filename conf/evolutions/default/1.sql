# --- !Ups

create table accounts (
  id_account                    bigint not null,
  user_data                     bigint,
  bank                          bigint,
  account                       varchar(255),
  constraint pk_accounts primary key (id_account)
);
create sequence accounts_seq;

create table bank (
  id_bank                       bigint not null,
  full_name                     varchar(255),
  bik                           integer,
  constraint pk_bank primary key (id_bank)
);
create sequence bank_seq;

create table bill (
  id_bill                       bigint not null,
  num                           integer,
  order_number                  integer,
  date                          timestamp,
  active_for                    timestamp,
  reciever                      integer,
  customer                      varchar(255),
  file                          blob,
  constraint pk_bill primary key (id_bill)
);
create sequence bill_seq;

create table users (
  id_user                       integer not null,
  login                         varchar(255),
  role                          integer,
  password                      varchar(255),
  constraint pk_users primary key (id_user)
);
create sequence users_seq;

create table user_data (
  id_user_data                  bigint not null,
  user                          integer,
  bill_prolongation             integer,
  inn                           integer,
  kpp                           integer,
  full_name                     varchar(255),
  adress                        varchar(255),
  phone                         varchar(255),
  constraint uq_user_data_user unique (user),
  constraint pk_user_data primary key (id_user_data)
);
create sequence user_data_seq;

alter table accounts add constraint fk_accounts_user_data foreign key (user_data) references user_data (id_user_data) on delete restrict on update restrict;
create index ix_accounts_user_data on accounts (user_data);

alter table accounts add constraint fk_accounts_bank foreign key (bank) references bank (id_bank) on delete restrict on update restrict;
create index ix_accounts_bank on accounts (bank);

alter table bill add constraint fk_bill_reciever foreign key (reciever) references users (id_user) on delete restrict on update restrict;
create index ix_bill_reciever on bill (reciever);

alter table user_data add constraint fk_user_data_user foreign key (user) references users (id_user) on delete restrict on update restrict;

INSERT INTO USERS VALUES(1, 'test_user', 1, 'test_user');
INSERT INTO bank VALUES(1, 'Тестовый банк', 123564987);
INSERT INTO user_data VALUES(1, 1, 10, 123456789, 999999999, 'ооо Тестовая организация', 'г.Тестовый, ул.Тестовая д.тестовый 443099','8937000000');
INSERT INTO accounts VALUES(1, 1, 1, '77777777777777777777777777');
INSERT INTO accounts VALUES(2, 1, 1, '12312312312312312312312312');
# --- !Downs

alter table accounts drop constraint if exists fk_accounts_user_data;
drop index if exists ix_accounts_user_data;

alter table accounts drop constraint if exists fk_accounts_bank;
drop index if exists ix_accounts_bank;

alter table bill drop constraint if exists fk_bill_reciever;
drop index if exists ix_bill_reciever;

alter table user_data drop constraint if exists fk_user_data_user;

drop table if exists accounts;
drop sequence if exists accounts_seq;

drop table if exists bank;
drop sequence if exists bank_seq;

drop table if exists bill;
drop sequence if exists bill_seq;

drop table if exists users;
drop sequence if exists users_seq;

drop table if exists user_data;
drop sequence if exists user_data_seq;

