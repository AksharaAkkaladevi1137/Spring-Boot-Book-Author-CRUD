-- Authors (10 rows)
INSERT INTO author (name, nationality, birth_year) VALUES ('George Orwell', 'British', 1903);
INSERT INTO author (name, nationality, birth_year) VALUES ('J.K. Rowling', 'British', 1965);
INSERT INTO author (name, nationality, birth_year) VALUES ('Mark Twain', 'American', 1835);
INSERT INTO author (name, nationality, birth_year) VALUES ('Leo Tolstoy', 'Russian', 1828);
INSERT INTO author (name, nationality, birth_year) VALUES ('Agatha Christie', 'British', 1890);
INSERT INTO author (name, nationality, birth_year) VALUES ('Ernest Hemingway', 'American', 1899);
INSERT INTO author (name, nationality, birth_year) VALUES ('Virginia Woolf', 'British', 1882);
INSERT INTO author (name, nationality, birth_year) VALUES ('Franz Kafka', 'Czech', 1883);
INSERT INTO author (name, nationality, birth_year) VALUES ('Gabriel Garcia Marquez', 'Colombian', 1927);
INSERT INTO author (name, nationality, birth_year) VALUES ('Fyodor Dostoevsky', 'Russian', 1821);

-- Books (10 rows)
INSERT INTO book (title, genre, published_year, isbn, author_id) VALUES ('1984', 'Dystopian', 1949, '978-0451524935', 1);
INSERT INTO book (title, genre, published_year, isbn, author_id) VALUES ('Harry Potter and the Philosopher''s Stone', 'Fantasy', 1997, '978-0439708180', 2);
INSERT INTO book (title, genre, published_year, isbn, author_id) VALUES ('Adventures of Huckleberry Finn', 'Adventure', 1884, '978-0486280615', 3);
INSERT INTO book (title, genre, published_year, isbn, author_id) VALUES ('War and Peace', 'Historical Fiction', 1869, '978-1400079988', 4);
INSERT INTO book (title, genre, published_year, isbn, author_id) VALUES ('Murder on the Orient Express', 'Mystery', 1934, '978-0062693662', 5);
INSERT INTO book (title, genre, published_year, isbn, author_id) VALUES ('The Old Man and the Sea', 'Literary Fiction', 1952, '978-0684801223', 6);
INSERT INTO book (title, genre, published_year, isbn, author_id) VALUES ('Mrs Dalloway', 'Modernist Fiction', 1925, '978-0156628709', 7);
INSERT INTO book (title, genre, published_year, isbn, author_id) VALUES ('The Metamorphosis', 'Absurdist Fiction', 1915, '978-0553213690', 8);
INSERT INTO book (title, genre, published_year, isbn, author_id) VALUES ('One Hundred Years of Solitude', 'Magical Realism', 1967, '978-0060883287', 9);
INSERT INTO book (title, genre, published_year, isbn, author_id) VALUES ('Crime and Punishment', 'Psychological Fiction', 1866, '978-0143107637', 10);
