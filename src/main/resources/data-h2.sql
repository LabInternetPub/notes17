/*INSERT INTO user_lab VALUES ('roure', 'Josep', 'Roure', 'roure@tecnocampus.cat', '$2a$10$0VGzG8lfiDXBnFTE98lfiOLtP4uh62wnE6iWs5.2AMrJ3G9k7XZqu', 1);
INSERT INTO user_lab VALUES ('alvarez', 'Sergi', 'Alvarez', 'alvarez@mail.cat', '$2a$10$7PFxXn4TQRiut9jNcAl7AubQZUWWck/eML3TDaQtoZiWNEN6o.Ig6', 1);
INSERT INTO user_lab VALUES ('castells', 'Esther', 'Castells', 'castells@mail.cat', '$2a$10$vvAubuSVVGFBx0I39N6EQeU4Z9rVcXadAyANbSfsuvdJ5VaVOfURS', 1);
INSERT INTO user_lab VALUES ('riera', 'Joana', 'Riera', 'riera@mail.cat', '$2a$10$wnw4VWrWAnFZmqPdWS5b1OtJxLyblMMNLImfbuFIm0hTUDp3q0SJy', 1);
INSERT INTO user_lab VALUES ('garcia', 'Marcel', 'Garcia', 'garcia@mail.cat', '$2a$10$ljdVKsjPBx.P61hjFNFVHOGsUe76QpRxxGBLG5WCgnPNlNhxr0thG', 1);
INSERT INTO user_lab VALUES ('lecina', 'Maria', 'Lecina', 'lecina@mail.cat', '$2a$10$8J6t9dJ7KCU1KGBd7E2/2enlG1tyNkzoswFbckO01Efwp6wF6MH7u', 1);
*/

INSERT INTO user_lab VALUES ('roure', 'Josep', 'Roure', 'roure@tecnocampus.cat', 'roure', 1);
INSERT INTO user_lab VALUES ('alvarez', 'Sergi', 'Alvarez', 'alvarez@mail.cat', 'alvarez', 1);
INSERT INTO user_lab VALUES ('castells', 'Esther', 'Castells', 'castells@mail.cat', 'castells', 1);
INSERT INTO user_lab VALUES ('riera', 'Joana', 'Riera', 'riera@mail.cat', 'riera', 1);
INSERT INTO user_lab VALUES ('garcia', 'Marcel', 'Garcia', 'garcia@mail.cat', 'garcia', 1);
INSERT INTO user_lab VALUES ('lecina', 'Maria', 'Lecina', 'lecina@mail.cat', 'lecina', 1);

INSERT INTO note_lab (title, content, date_creation, date_edit, owner) VALUES ('spring', 'va super be', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'roure');
INSERT INTO note_lab (title, content, date_creation, date_edit, owner) VALUES ('spring boot', 'va encara millor', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'roure');
