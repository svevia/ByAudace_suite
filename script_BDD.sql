CREATE TABLE utilisateur(
	mail CHAR(200) PRIMARY KEY NOT NULL,
	nom CHAR(200),
	prenom CHAR(200),
	digit CHAR(20),
	mot_de_passe CHAR(50),
	demande INT NOT NULL DEFAULT 0,
	consultation INT NOT NULL DEFAULT 0,
	role CHAR(50)
);

CREATE TABLE phrase_metier(
	phrase CHAR(300) PRIMARY KEY NOT NULL,
	besoin CHAR(300),
	mail_deposant CHAR(200),
	terminee BOOLEAN NOT NULL DEFAULT False,
	consultee INT NOT NULL DEFAULT 0,
	FOREIGN KEY(mail_deposant) REFERENCES utilisateur(mail) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE aide(
	mail_deposant CHAR(200),
	mail_repondant CHAR(200),
	phrase CHAR(300),
	FOREIGN KEY(mail_deposant) REFERENCES phrase_metier(mail_deposant) ON UPDATE CASCADE ON DELETE CASCADE,	
	FOREIGN KEY(mail_repondant) REFERENCES utilisateur(mail) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(phrase) REFERENCES phrase_metier(phrase) ON UPDATE CASCADE ON DELETE CASCADE,
	PRIMARY KEY(mail_deposant,mail_repondant,phrase)
);