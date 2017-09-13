DROP TABLE if EXISTS user_lab;
CREATE TABLE user_lab
(
  username VARCHAR (55) PRIMARY KEY,
  name VARCHAR (255),
  second_name VARCHAR (255),
  email VARCHAR (100)
);

DROP TABLE if EXISTS note_lab;
CREATE TABLE note_lab (
  id bigint auto_increment PRIMARY KEY,
  title VARCHAR (255) ,
  content VARCHAR (255),
  date_creation TIMESTAMP ,
  date_edit TIMESTAMP ,
  owner VARCHAR (55),
  FOREIGN KEY (owner)
  REFERENCES user_lab(username)
)