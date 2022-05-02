DROP TABLE IF EXISTS AuthCode;
DROP TABLE IF EXISTS Banner;
DROP TABLE IF EXISTS Lockup;
DROP TABLE IF EXISTS LoginHistory;
DROP TABLE IF EXISTS Member;
DROP TABLE IF EXISTS Notice;
DROP TABLE IF EXISTS Point;
DROP TABLE IF EXISTS PointHistory;
DROP TABLE IF EXISTS Wallet;
DROP TABLE IF EXISTS WalletHistory;
DROP TABLE IF EXISTS KeyValue;

create table AuthCode (
                          id bigint not null auto_increment,
                          type varchar(255),
                          authenticate varchar(255),
                          code varchar(255),
                          status integer,
                          createdTime timestamp DEFAULT current_timestamp,
                          updatedTime timestamp on update current_timestamp,
                          expiredTime timestamp,
                          primary key (id)
);

create table Banner (
                        id bigint not null auto_increment,
                        bannerImage varchar(255),
                        detailImage varchar(255),
                        link varchar(255),
                        priority integer,
                        status integer,
                        createdTime timestamp DEFAULT current_timestamp,
                        updatedTime timestamp on update current_timestamp,
                        primary key (id)
);

create table Lockup (
                        id bigint not null auto_increment,
                        amount decimal(28,18),
                        comment varchar(255),
                        memberId bigint,
                        status bit,
                        startTime timestamp,
                        endTime timestamp,
                        createdTime timestamp DEFAULT current_timestamp,
                        updatedTime timestamp on update current_timestamp,
                        primary key (id)
);

create table LoginHistory (
                              id bigint not null auto_increment,
                              device varchar(255),
                              memberId bigint,
                              createdTime timestamp DEFAULT current_timestamp,
                              updatedTime timestamp on update current_timestamp,
                              primary key (id)
);

create table Member (
                        id bigint not null auto_increment,
                        email varchar(255),
                        fcmToken varchar(255),
                        password varchar(255),
                        passwordFailCnt integer,
                        phone varchar(255),
                        pin varchar(255),
                        pinFailCnt integer,
                        reward varchar(255),
                        status integer,
                        type varchar(255),
                        createdTime timestamp DEFAULT current_timestamp,
                        updatedTime timestamp on update current_timestamp,
                        deletedTime timestamp,
                        primary key (id)
);

create table Notice (
                        id bigint not null auto_increment,
                        title varchar(255),
                        contents varchar(255),
                        status integer,
                        writerId integer,
                        createdTime timestamp DEFAULT current_timestamp,
                        updatedTime timestamp on update current_timestamp,
                        deletedTime timestamp,
                        primary key (id)
);
create table Point (
                       id bigint not null auto_increment,
                       balance decimal(28,18),
                       memberId bigint,
                       createdTime timestamp DEFAULT current_timestamp,
                       updatedTime timestamp on update current_timestamp,
                       primary key (id)
);

create table PointHistory (
                              id bigint not null auto_increment,
                              amount decimal(28,18),
                              balance decimal(28,18),
                              memberId bigint,
                              prevBalance decimal(28,18),
                              type varchar(255),
                              createdTime timestamp DEFAULT current_timestamp,
                              updatedTime timestamp on update current_timestamp,
                              primary key (id)
);

create table Wallet (
                        id bigint not null auto_increment,
                        address varchar(255),
                        balance decimal(28,18),
                        memberId bigint,
                        symbol varchar(255),
                        createdTime timestamp DEFAULT current_timestamp,
                        updatedTime timestamp on update current_timestamp ,
                        primary key (id)
);

create table WalletHistory (
                               id bigint not null auto_increment,
                               amount decimal(28,18),
                               balance decimal(28,18),
                               companyFee decimal(28,18),
                               fee decimal(28,18),
                               fromAddress varchar(255),
                               gas decimal(28,18),
                               status integer,
                               toAddress varchar(255),
                               txId varchar(255),
                               type varchar(255),
                               walletId integer,
                               createdTime timestamp DEFAULT current_timestamp,
                               updatedTime timestamp on update current_timestamp,
                               primary key (id)
);

CREATE TABLE KeyValue (
                            `key` varchar(255) not null,
                            `value` varchar(255) not null,
                            PRIMARY KEY (`key`)
);