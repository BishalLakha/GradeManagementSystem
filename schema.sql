CREATE TABLE IF NOT EXISTS Class (
    id INT AUTO_INCREMENT PRIMARY KEY,
    course_number VARCHAR(255),
    term VARCHAR(255) NOT NULL,
    section_number INT NOT NULL,
    description TEXT
);



CREATE TABLE IF NOT EXISTS Category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL,
    class_id INT NOT NULL REFERENCES Class(id),
    weight INT NOT NULL    
);



CREATE TABLE IF NOT EXISTS Assignment (
	id INT AUTO_INCREMENT PRIMARY KEY,
    class_id INT NOT NULL REFERENCES Class(id),
    name VARCHAR(255) NOT NULL,
	category INT NOT NULL REFERENCES Category(id),
    description TEXT,
    points INT NOT NULL
);



CREATE TABLE IF NOT EXISTS Student (
    username VARCHAR(255) UNIQUE PRIMARY KEY,
    student_id INT NOT NULL ,
    last_name VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
) ;



CREATE TABLE IF NOT EXISTS Enroll (
	username VARCHAR(255) NOT NULL REFERENCES Student(username),
	class_id INT NOT NULL REFERENCES Class(id)
);



CREATE TABLE IF NOT EXISTS Solution (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL REFERENCES Student(username),
    assignment_name INT NOT NULL REFERENCES Assignment(id),
    grade FLOAT
) ;