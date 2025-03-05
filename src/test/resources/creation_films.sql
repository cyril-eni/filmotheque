-- ATTENTION : PAS de double quotes ("") dans les values() sinon ca fait planter la requête d'insert'
insert into genre(id,titre) values(1, 'Science-Fiction');
insert into genre(id,titre) values(2, 'Documentaire');
insert into genre(id,titre) values(3, 'Action');
insert into genre(id,nom) values(4, 'Comédie');
insert into genre(id,nom) values(5, 'Drame');

insert into Participant(id,prenom, nom) values(1, 'Steven', 'Spielberg');
insert into Participant(id,prenom, nom) values(2, 'David', 'Cronenberg');
insert into Participant(id,prenom, nom) values(3, 'Dany', 'Boon');

insert into Film(id,annee,titre,genre_id,realisateur_id) values(1, 1993, 'Jurassic Park', 1, 1);
insert into Film(id,annee,titre,genre_id,realisateur_id) values(2,1986, 'The Fly', 1, 2);
insert into Film(id,annee,titre,genre_id,realisateur_id) values(3,2016, 'The BFG', 4, 1);
insert into Film(id,annee,titre,genre_id,realisateur_id) values(4,2008, 'Bienvenue chez les Ch''tis', 4, 4);