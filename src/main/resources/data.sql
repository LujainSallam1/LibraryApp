INSERT INTO book (bookid, isbn, title, authors, publish_date) VALUES
    ('1', '9780446310789', 'To Kill a Mockingbird', 'Harper Lee', '1960-07-11'),
    ('2', '9780743273565', 'The Great Gatsby', 'F. Scott Fitzgerald Linda Cookson', '1925-04-10'),
    ('3', '9780006546061', 'Fahrenheit 451', 'Fahrenheit 451', '1953-10-13'),
    ('4', '9780140283334', 'Lord of the Flies', 'William Golding', '1954-09-17'),
    ('5', '9780142000670', 'Of Mice and Men', 'John Steinbeck', '1937-02-25'),
    ('6', '9780060929879', 'Brave New World', 'Brave New World', '1932-09-01'),
    ('7', '9780316769174', 'The Catcher in the Rye', 'J.D. Salinger', '1951-07-16'),
    ('8', '9780141036144', '1984', 'George Orwell', '1949-04-08'),
    ('9', '9780393320978', 'Beowulf', 'Unknown', '900-01-01'),
    ('10', '9780060502935', 'Going Postal', 'Terry Pratchett', '2004-09-25');

INSERT INTO bluray (blurayid, title, directors, actors, release_date) VALUES
    ('1', 'Inception', 'Christopher Nolan', 'Leonardo DiCaprio, Ellen Page', '2010-07-16'),
    ('2', 'The Dark Knight', 'Christopher Nolan', 'Christian Bale, Heath Ledger', '2008-07-18'),
    ('3', 'Interstellar', 'Christopher Nolan', 'Matthew McConaughey, Anne Hathaway', '2014-11-07'),
    ('4', 'Avengers: Endgame', 'Anthony Russo, Joe Russo', 'Robert Downey Jr., Chris Evans', '2019-04-26'),
    ('5', 'The Shawshank Redemption', 'Frank Darabont', 'Tim Robbins, Morgan Freeman', '1994-09-23'),
    ('6', 'Pulp Fiction', 'Quentin Tarantino', 'John Travolta, Uma Thurman', '1994-10-14'),
    ('7', 'The Godfather', 'Francis Ford Coppola', 'Marlon Brando, Al Pacino', '1972-03-24'),
    ('8', 'Forrest Gump', 'Robert Zemeckis', 'Tom Hanks, Robin Wright', '1994-07-06'),
    ('9', 'The Matrix', 'The Wachowski Brothers', 'Keanu Reeves, Laurence Fishburne', '1999-03-31'),
    ('10', 'Jurassic Park', 'Steven Spielberg', 'Sam Neill, Laura Dern', '1993-06-11');

INSERT INTO member (memberid, name, street, zipcode, city) VALUES
    ('1', 'Pieter de Vries', 'Veerstraat 12', '1234 AB', 'Amsterdam'),
    ('2', 'Marianne Jansen', 'Beukenlaan 34', '5678 CD', 'Rotterdam'),
    ('3', 'Johan van der Berg', 'Kerkweg 56', '9012 EF', 'Utrecht'),
    ('4', 'Annelies van Dijk', 'Dorpsplein 78', '3456 GH', 'Groningen'),
    ('5', 'Michiel Bakker', 'Havenstraat 90', '7890 IJ', 'The Hague'),
    ('6', 'Ingrid Visser', 'Schoolstraat 23', '2345 KL', 'Eindhoven'),
    ('7', 'Peter Smit', 'Parklaan 45', '6789 MN', 'Amersfoort'),
    ('8', 'Saskia de Boer', 'Industrieweg 67', '1234 PQ', 'Haarlem'),
    ('9', 'Willem van Leeuwen', 'Kloosterweg 89', '5678 RS', 'Leiden'),
    ('10', 'Carla Mulder', 'Prinsenlaan 12', '9012 ST', 'Nijmegen');
