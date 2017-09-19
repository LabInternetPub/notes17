INSERT INTO user_lab VALUES ('roure', 'Josep', 'Roure', 'roure@tecnocampus.cat', 'roure', 1);
INSERT INTO user_lab VALUES ('alvarez', 'Sergi', 'Alvarez', 'alvarez@mail.cat', 'alvarez', 1);
INSERT INTO user_lab VALUES ('castells', 'Esther', 'Castells', 'castells@mail.cat', 'castells', 1);
INSERT INTO user_lab VALUES ('riera', 'Joana', 'Riera', 'riera@mail.cat', 'riera', 1);
INSERT INTO user_lab VALUES ('garcia', 'Marcel', 'Garcia', 'garcia@mail.cat', 'garcia', 1);
INSERT INTO user_lab VALUES ('lecina', 'Maria', 'Lecina', 'lecina@mail.cat', 'lecina', 1);

INSERT INTO note_lab (title, content, date_creation, date_edit, owner) VALUES ('spring', 'va super be', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'roure');
INSERT INTO note_lab (title, content, date_creation, date_edit, owner) VALUES ('spring boot', 'va encara millor', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'roure');
