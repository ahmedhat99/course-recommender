INSERT INTO Author (id, name, email, birthdate)
VALUES (1, 'Author', 'author@example.com', '2000-01-01');

INSERT INTO Course (id, name, description, credit, author_id)
VALUES (1, 'Course 1', 'Description for Course 1', 3, 1),
       (2, 'Course 2', 'Description for Course 2', 4, 1),
       (3, 'Course 3', 'Description for Course 3', 2, 1),
       (4, 'Course 4', 'Description for Course 4', 3, 1),
       (5, 'Course 5', 'Description for Course 5', 4, 1);