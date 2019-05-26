drop database if exists PollWeb;
create database PollWeb;
use PollWeb;

create table responsibleUser (
ID integer unsigned not null primary key auto_increment,
nameR varchar(100) not null,
surnameR varchar(100),
fiscalCode varchar(16) unique,
email varchar(100) not null,
pwd varchar(16) not null
);

create table poll (
ID integer unsigned not null primary key auto_increment,
title varchar(100) unique not null,
apertureText varchar(500),
closerText varchar(200),
typeP enum('open','reserved') default 'open',
url varchar(100) not null unique,
activated enum('0','1') not null default '0',
idR integer unsigned not null,
foreign key (idR) references responsibleUser(ID) 
);

create table reservedUser(
ID integer unsigned not null primary key auto_increment,
nameU varchar(100),
surnameU varchar(100),
email varchar(100) not null,
pwd varchar(16) not null unique, 
compiled enum('0','1') default '0' not null,
idP integer unsigned not null,
foreign key (idP) references poll(ID) on delete cascade on update cascade
);

create table normalUser (
ID integer unsigned not null primary key auto_increment,
nameN varchar(100)
);

create table openQuestion (
ID integer unsigned not null primary key auto_increment,
textO varchar(500) not null unique,
typeO enum('short text','long text','date','number') not null unique,
note varchar(200),
mindate date,
maxdate date,
valmin integer,
valmax integer,
obbligated enum('yes','no') not null default 'no',
positionO integer,
idP integer unsigned not null,
foreign key (idP) references poll(ID) on delete cascade on update cascade
);

create table multipleChoiceQuestion(
ID integer unsigned not null primary key auto_increment,
textM varchar(500) not null unique,
typeM enum('Single choice','Multiple choice') not null,
minM integer,
maxM integer,
note varchar(200),
obbligated enum('yes','no') not null default 'no',
positionM integer,
idP integer unsigned not null,
foreign key (idP) references poll(ID) on delete cascade on update cascade
);

create table possibleChoice (
ID integer unsigned not null primary key auto_increment,
textPC varchar (200),
idM integer unsigned not null,
foreign key (idM) references multipleChoiceQuestion(ID) on delete cascade on update cascade
);

create table openAnswer(
ID integer unsigned not null primary key auto_increment,
textA varchar(500),
dateA date,
numA integer,
idNU integer unsigned not null,
idRU integer unsigned not null,
foreign key (idNU) references normalUser(ID),
foreign key (idRU) references reservedUser(ID)
);

create table multipleChoiceAnswer (
ID integer unsigned not null primary key auto_increment,
idPC integer unsigned not null,
idNU integer unsigned not null,
idRU integer unsigned not null,
foreign key (idNU) references normalUser(ID),
foreign key (idRU) references reservedUser(ID),
foreign key (idPC) references possibleChoice(ID) on delete cascade
);