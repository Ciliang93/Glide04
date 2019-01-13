-- *********************************************
-- * Standard SQL generation                   
-- *--------------------------------------------
-- * DB-MAIN version: 10.0.3              
-- * Generator date: Aug 17 2017              
-- * Generation date: Wed May  9 09:13:26 2018 
-- * LUN file: C:\Users\CLS\Documents\Java\glide04\analyse\schemaLogique.lun 
-- * Schema: version 1.0/SQL1 
-- ********************************************* 


-- Database Section
-- ________________ 


-- DBSpace Section
-- _______________


-- Tables Section
-- _____________ 

create table Pilote (
     id serial not null,
     email TEXT not null,
     nom TEXT not null,
     prenom TEXT not null,
     adresse TEXT not null,
     noGsm TEXT not null,
     solde float not null,
     constraint ID_Pilote_ID primary key (id));

create table Planeur (
     id serial not null,
     type TEXT not null,
     prixHoraire float not null,
     prixFixe float not null,
     constraint ID_Planeur_ID primary key (id));

create table Vol (
     id serial not null,
     dateVol date not null,
     duree numeric not null,
     prix float not null,
     idPlaneur int not null,
     idPilote int not null,
     constraint ID_Vol_ID primary key (id));


-- Constraints Section
-- ___________________ 

alter table vol add constraint REF_Vol_Plane_FK
     foreign key (idPlaneur)
     references Planeur;

alter table vol add constraint REF_Vol_Pilot_FK
     foreign key (idPilote)
     references Pilote;


-- Index Section
-- _____________ 

create unique index ID_Pilote_IND
     on Pilote (id);

create unique index ID_Planeur_IND
     on Planeur (id);

create unique index ID_Vol_IND
     on Vol (id);

create index REF_Vol_Plane_IND
     on Vol (idPlaneur);

create index REF_Vol_Pilot_IND
     on Vol (idPilote);

