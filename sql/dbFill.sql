
-- Remplissage de la table planeur
-- _______________________________

INSERT INTO planeur(type, prixhoraire, prixfixe) VALUES ('bois et toile', 17, 25); -- ID = 1
INSERT INTO planeur(type, prixhoraire, prixfixe) VALUES ('plastique', 27, 25); -- ID = 2
INSERT INTO planeur(type, prixhoraire, prixfixe) VALUES ('biplace', 30, 25); -- ID = 3

-- Remplissage de la table pilote
-- _______________________________

INSERT INTO pilote(email, nom, prenom, adresse, nogsm, solde) VALUES ('colson.junior@gmail.com', 'Colson', 'Junior', 'Rue Willy Ernst 7-9, 6000 Charleroi', '0477906985', 200); -- ID = 1
INSERT INTO pilote(email, nom, prenom, adresse, nogsm, solde) VALUES ('ciliberto.angelo@gmail.com', 'Ciliberto', 'Angelo', 'Rue de la place 18, 4000 Liege', '0478485147', 0); -- ID = 2
INSERT INTO pilote(email, nom, prenom, adresse, nogsm, solde) VALUES ('manvussa.gerard@gmail.com', 'Manvussa', 'Gerard', 'Rue duu calembour 85, 5000 Namur', '0497406345', -150); -- ID = 3
INSERT INTO pilote(email, nom, prenom, adresse, nogsm, solde) VALUES ('luation.eva@gmail.com', 'Luation', 'Eva', 'Rue des epreuves 20, 1000 Bruxelles', '0467513945', 26); -- ID = 4
INSERT INTO pilote(email, nom, prenom, adresse, nogsm, solde) VALUES ('tencier.penny@gmail.com', 'Tencier', 'Penny', 'Rue des cellules 12, 1000 Bruxelles', '0499548624', -500); -- ID = 5

-- Remplissage de la table vol
-- _______________________________

INSERT INTO vol(datevol, duree, prix, idplaneur, idpilote) VALUES ( TO_DATE('14/08/2017','DD/MM/YYYY'), 60, 42, 1, 1 );
INSERT INTO vol(datevol, duree, prix, idplaneur, idpilote) VALUES ( TO_DATE('15/08/2017','DD/MM/YYYY'), 60, 52, 2, 2 );
INSERT INTO vol(datevol, duree, prix, idplaneur, idpilote) VALUES ( TO_DATE('15/08/2017','DD/MM/YYYY'), 60, 55, 3, 3 );
INSERT INTO vol(datevol, duree, prix, idplaneur, idpilote) VALUES ( TO_DATE('15/08/2017','DD/MM/YYYY'), 60, 42, 1, 4 );
INSERT INTO vol(datevol, duree, prix, idplaneur, idpilote) VALUES ( TO_DATE('16/08/2017','DD/MM/YYYY'), 60, 52, 2, 5 );

