drop database if exists PollWeb;
create database PollWeb;
use PollWeb;

create table Responsible (
ID integer unsigned not null primary key auto_increment,
namer varchar(100) not null,
surname varchar(100),
fiscalCode varchar(16) unique,
email varchar(100) not null,
pwd varchar(16) not null
);

create table Poll (
ID integer unsigned not null primary key auto_increment,
title varchar(100) unique not null,
apertureText varchar(500),
closerText varchar(200),
typeP enum('open','reserved') default 'open',
url varchar(100) not null unique,
activated enum('0','1') not null default '0',
idR integer unsigned not null,
constraint Create_poll foreign key (idR) references Responsible(ID) 
);

create table reservedUser(
ID integer unsigned not null primary key auto_increment,
nameu varchar(100),
surname varchar(100),
email varchar(100) not null,
pwd varchar(16) not null unique, 
compiled enum('0','1') default '0' not null,
idP integer unsigned not null,
constraint Acess_poll foreign key (idP) references Poll(ID) on delete cascade on update cascade
);

create table normalUser (
ID integer unsigned not null primary key auto_increment,
namen varchar(100)
);

create table OpenQuestion (
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
constraint Composed_Poll foreign key (idP) references Poll(ID) on delete cascade on update cascade
);

create table MultipleChoice(
ID integer unsigned not null primary key auto_increment,
textM varchar(500) not null unique,
typeM enum('Single choice','Multiple choice') not null,
minM integer,
maxM integer,
note varchar(200),
obbligated enum('yes','no') not null default 'no',
positionM integer,
idP integer unsigned not null,
constraint comp_Poll foreign key (idP) references Poll(ID) on delete cascade on update cascade
);

create table PossibleChoice (
ID integer unsigned not null primary key auto_increment,
textPC varchar (200),
idM integer unsigned not null,
constraint CheckBox_choice foreign key (idM) references MultipleChoice(ID) on delete cascade on update cascade
);

create table openAnswer(
ID integer unsigned not null primary key auto_increment,
textA varchar(500),
dateA date,
numA integer,
idNU integer unsigned not null,
idRU integer unsigned not null,
constraint Answer_normal foreign key (idNU) references NormalUser(ID),
constraint Answer_reserved foreign key (idRU) references ReservedUser(ID)
);

create table MultipleChoiceAnswer (
ID integer unsigned not null primary key auto_increment,
idPC integer unsigned not null,
idNU integer unsigned not null,
idRU integer unsigned not null,
constraint MultipleAnswer_normal foreign key (idNU) references NormalUser(ID),
constraint MultipleAnswer_reserved foreign key (idRU) references ReservedUser(ID),
constraint answer_possibleChoice foreign key (idPC) references PossibleChoice(ID) on delete cascade
);