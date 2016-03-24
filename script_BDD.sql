CREATE TABLE utilisateur(
	mail CHAR(200) PRIMARY KEY NOT NULL,
	nom CHAR(200),
	prenom CHAR(200),
	digit CHAR(20),
	numero INT,
	mot_de_passe CHAR(50) NOT NULL,
	role CHAR(50)
);

CREATE TABLE phrase_metier(
	phrase CHAR(300) PRIMARY KEY NOT NULL,
	besoin CHAR(300),
	mail_deposant CHAR(200),
	terminee BOOLEAN DEFAULT False,
	consultee INT DEFAULT 0,
	FOREIGN KEY(mail_deposant) REFERENCES utilisateur(mail) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE aide(
	mail_repondant CHAR(200),
	phrase CHAR(300),
	date DATETIME,
	FOREIGN KEY(mail_repondant) REFERENCES utilisateur(mail) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(phrase) REFERENCES phrase_metier(phrase) ON UPDATE CASCADE ON DELETE CASCADE,
	PRIMARY KEY(mail_repondant,phrase)
);

INSERT INTO utilisateur VALUES("toto@gmail.com","toto","tototo","FRNL","toto","user");
INSERT INTO utilisateur VALUES("titi@gmail.com","titi","tititi","FRDM","titi","admin");
INSERT INTO utilisateur VALUES("tata@gmail.com","tata","tatata","FRPO","tata","user");

INSERT INTO phrase_metier VALUES("mon entreprise demarre","besoin d'un secretaire","toto@gmail.com",NULL,NULL);