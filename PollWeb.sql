drop database if exists PollWeb;
create database PollWeb;
use PollWeb;


create table responsibleUser (
ID integer unsigned not null primary key auto_increment,
nameR varchar(100) not null,
surnameR varchar(100),
fiscalCode varchar(16) unique,
email varchar(100) not null,
pwd varchar(16) not null,
administrator enum('yes', 'no') not null default 'no',
accepted boolean not null default 0
);

create table poll (
ID integer unsigned not null primary key auto_increment,
title varchar(100) unique not null,
apertureText varchar(500),
closerText varchar(200),
typeP enum('open','reserved') default 'open',
url varchar(100) unique,
activated enum('0','1') not null default '0',
idR integer unsigned not null,
foreign key (idR) references responsibleUser(ID) 
);

create table participant (
ID integer unsigned not null primary key auto_increment,
apiKey varchar(100) unique not null,
nameP varchar (200),
email varchar (200),
pwd varchar (200)
);

create table participation (
ID integer unsigned not null primary key auto_increment,
ID_poll integer unsigned not null,
ID_part integer unsigned not null,
foreign key (ID_poll) references poll(ID) on delete cascade on update cascade,
foreign key (ID_part) references participant(ID) 
);

create table question (
ID integer unsigned not null primary key auto_increment,
typeq enum('short text', 'long text', 'numeric', 'date' , 'single choice' , 'multiple choice')  not null,
textq varchar(500) not null,
note varchar(200),
obbligation enum('yes', 'no') not null default 'no',
possible_answer json,
IDP integer unsigned not null,
foreign key (IDP) references poll(ID)
);

create table answer (
ID integer unsigned not null primary key auto_increment,
IDQ integer unsigned not null,
ID_P integer unsigned not null,
texta json not null,
foreign key (IDQ) references question(ID) on delete cascade on update cascade,
foreign key (ID_P) references participant(ID) on delete cascade on update cascade
);



