CREATE TABLE Author (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    birthdate DATE
);

CREATE TABLE Course (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    description VARCHAR(255),
    credit INT,
    author_id INT,
    FOREIGN KEY (author_id) REFERENCES Author(id)
);

CREATE TABLE Rating (
    id INT PRIMARY KEY,
    number INT,
    course_id INT,
    FOREIGN KEY (course_id) REFERENCES Course(id)
);

CREATE TABLE Assessment (
    id INT PRIMARY KEY,
    content VARCHAR(255),
    course_id INT,
    FOREIGN KEY (course_id) REFERENCES Course(id)
);
