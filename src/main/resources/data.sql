INSERT INTO user_lab VALUES ('roure', 'Josep', 'Roure', 'roure@tecnocampus.cat');
INSERT INTO user_lab VALUES ('alvarez', 'Sergi', 'Alvarez', 'alvarez@mail.cat');
INSERT INTO user_lab VALUES ('castells', 'Esther', 'Castells', 'castells@mail.cat');
INSERT INTO user_lab VALUES ('riera', 'Joana', 'Riera', 'riera@mail.cat');
INSERT INTO user_lab VALUES ('garcia', 'Marcel', 'Garcia', 'garcia@mail.cat');
INSERT INTO user_lab VALUES ('lecina', 'Maria', 'Lecina', 'lecina@mail.cat');

INSERT INTO note_lab (title, content, date_creation, date_edit, owner) VALUES ('spring', 'va super be', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'roure');
INSERT INTO note_lab (title, content, date_creation, date_edit, owner) VALUES ('spring boot', 'va encara millor', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'roure');
