SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE sus.authors;
TRUNCATE TABLE sus.books;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO sus.authors(name) VALUES ('Steven King');
INSERT INTO sus.authors(name) VALUES ('Erich Maria Remark');
INSERT INTO sus.authors(name) VALUES ('Ceneca');
INSERT INTO sus.authors(name) VALUES ('Shakespeare');

INSERT INTO sus.books(authorId, name, price) VALUES (2, '#1', 100);
INSERT INTO sus.books(authorId, name, price) VALUES (2, '#2', 120);
INSERT INTO sus.books(authorId, name, price) VALUES (2, '#3', 50);

INSERT INTO sus.books(authorId, name, price) VALUES (1, '#1', 30);
INSERT INTO sus.books(authorId, name, price) VALUES (1, '#2', 200);

INSERT INTO sus.books(authorId, name, price) VALUES (3, '#1', 90);
INSERT INTO sus.books(authorId, name, price) VALUES (3, '#2', 400);
INSERT INTO sus.books(authorId, name, price) VALUES (3, '#3', 500);

INSERT INTO sus.books(authorId, name, price) VALUES (4, '#1', 1000);
INSERT INTO sus.books(authorId, name, price) VALUES (4, '#2', 20);