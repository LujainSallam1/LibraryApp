INSERT INTO book (id, isbn, title, authors, publish_date)
VALUES ('1', '9780446310789', 'To Kill a Mockingbird', 'Harper Lee', '1960-07-11'),
       ('2', '9780743273565', 'The Great Gatsby', 'F. Scott Fitzgerald Linda Cookson', '1925-04-10'),
       ('3', '9780006546061', 'Fahrenheit 451', 'Fahrenheit 451', '1953-10-13'),
       ('4', '9780140283334', 'Lord of the Flies', 'William Golding', '1954-09-17'),
       ('5', '9780142000670', 'Of Mice and Men', 'John Steinbeck', '1937-02-25'),
       ('6', '9780060929879', 'Brave New World', 'Brave New World', '1932-09-01'),
       ('7', '9780316769174', 'The Catcher in the Rye', 'J.D. Salinger', '1951-07-16'),
       ('8', '9780141036144', '1984', 'George Orwell', '1949-04-08'),
       ('9', '9780393320978', 'Beowulf', 'Unknown', '900-01-01'),
       ('10', '9780060502935', 'Going Postal', 'Terry Pratchett', '2004-09-25');

INSERT INTO member (id, naam, adres, woonplaats)
VALUES ('1', 'Chezley', 'Epsilonplantsoen', 'Leiden'),
       ('2', 'Lujain', 'Haven', 'Leiden' ),
       ('3', 'Laurinde', 'Omegaplantsoen' , 'Leiden' ),
       ('4', 'Lieke', 'Deltaweg', 'Leiden');
INSERT INTO Bluerays (id, title, director, publish_date, borrowed, genre)
VALUES
    ('1', 'Avatar', 'James Cameron', '2009-12-18', false, 'Action, Adventure, Fantasy'),
    ('2', 'Inception', 'Christopher Nolan', '2010-07-16', false, 'Action, Adventure, Sci-Fi'),
    ('3', 'The Dark Knight', 'Christopher Nolan', '2008-07-18', true, 'Action, Crime, Drama'),
    ('4', 'Interstellar', 'Christopher Nolan', '2014-11-07', false, 'Adventure, Drama, Sci-Fi'),
    ('5', 'The Shawshank Redemption', 'Frank Darabont', '1994-10-14', true, 'Drama'),
    ('6', 'The Godfather', 'Francis Ford Coppola', '1972-03-24', false, 'Crime, Drama'),
    ('7', 'Pulp Fiction', 'Quentin Tarantino', '1994-10-14', true, 'Crime, Drama'),
    ('8', 'The Matrix', 'Lana Wachowski, Lilly Wachowski', '1999-03-31', true, 'Action, Sci-Fi');
