CREATE DATABASE IF NOT EXISTS Course_Management_System;
#DROP DATABASE Course_Management_System;
USE Course_Management_System;

CREATE TABLE Modules
(
module_id VARCHAR (10),
module_name VARCHAR(55),
program_name VARCHAR(55),
lecturer_id VARCHAR(12),
room_id VARCHAR(15)
);

CREATE TABLE program
(
program_name VARCHAR(55),
program_id VARCHAR(12)
);

CREATE TABLE Students
(
student_id VARCHAR (12),
student_name VARCHAR(100),
student_email VARCHAR(100),
student_phone VARCHAR(16),
program_name VARCHAR(85)
);

CREATE TABLE enrollment
(
enrollment_id VARCHAR (10),
student_id VARCHAR (12),
program_id VARCHAR(12),
enrollment_date DATE,
finishing_date DATE 
);

CREATE TABLE lecturers
(
lecturer_id VARCHAR(12),
lecturer_name VARCHAR (55),
module_id VARCHAR (10),
job_role VARCHAR (50),
teaching_classes VARCHAR (50)
);

CREATE TABLE rooms
(
room_id VARCHAR(25),
room_name VARCHAR (55)
);

CREATE TABLE grades
(
student_id VARCHAR (12),
module_id VARCHAR (10),
grades INT CHECK(grades >=0 AND grades <=100) 
);

CREATE TABLE feedback
(
feedback_id INT AUTO_INCREMENT PRIMARY KEY,
student_id VARCHAR (12),
feedback_text TEXT,
feeback_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

SELECT * FROM lecturers;

ALTER TABLE Modules ADD PRIMARY KEY (module_id);

ALTER TABLE program ADD PRIMARY KEY (program_name);

ALTER TABLE Students ADD PRIMARY KEY (student_id);

ALTER TABLE enrollment ADD PRIMARY KEY (enrollment_id);

ALTER TABLE rooms ADD PRIMARY KEY (room_id);

# ALTER TABLE Students DROP PRIMARY KEY;

ALTER TABLE Modules ADD CONSTRAINT fk_program_name FOREIGN KEY (program_name) REFERENCES program(program_name);

ALTER TABLE Students ADD CONSTRAINT fk_program_name_students FOREIGN KEY (program_name) REFERENCES program(program_name);

ALTER TABLE Modules ADD CONSTRAINT fk_room_id FOREIGN KEY (room_id) REFERENCES rooms(room_id);

ALTER TABLE enrollment ADD CONSTRAINT fk_student_id FOREIGN KEY (student_id) REFERENCES Students(student_id);

ALTER TABLE lecturers ADD CONSTRAINT module_id FOREIGN KEY (module_id) REFERENCES Modules(module_id);

ALTER TABLE grades ADD CONSTRAINT student_id FOREIGN KEY (student_id) REFERENCES Students(student_id);

ALTER TABLE grades ADD CONSTRAINT fk_module_id FOREIGN KEY (module_id) REFERENCES Modules(module_id);

ALTER TABLE feedback ADD CONSTRAINT fk1_student_id FOREIGN KEY (student_id) REFERENCES Students(student_id);

#currently attending school
SELECT *
FROM Students s
JOIN enrollment e ON s.student_id = e.student_id
WHERE e.finishing_date > '2024-03-19';

#how many are attending each program
SELECT p.program_name, COUNT(s.student_name) AS total_students
FROM program p
LEFT JOIN Students s on p.program_name = s.program_name
GROUP BY p.program_name;

#how many are attending each program and are not finished
SELECT p.program_name, COUNT(s.student_name) AS total_students
FROM program p
LEFT JOIN Students s on p.program_name = s.program_name
LEFT JOIN enrollment e ON s.student_id = e.student_id
WHERE e.finishing_date >"2024-03-19" 
GROUP BY p.program_name;

#people who failed a module and scored below 40
SELECT s.student_name, m.module_name, g.grades AS grade
FROM Students s
JOIN grades g ON s.student_id = g.student_id
JOIN Modules m ON g.module_id = m.module_id
WHERE g.grades < 40;

# average grade per module
SELECT m.module_id, m.module_name, AVG(g.grades) AS avg_grade
FROM Modules m
JOIN grades g ON m.module_id = g.module_id
GROUP BY m.module_id, m.module_name
ORDER BY avg_grade DESC;

#course report 
-- Lock the tables being read
LOCK TABLES Modules m READ, program p READ, Students s READ, enrollment e READ, lecturers l READ, rooms r READ;

SELECT m.module_name, p.program_name, COUNT(DISTINCT e.student_id) AS num_students_enrolled, l.lecturer_name, r.room_name
FROM Modules m
JOIN program p ON m.program_name = p.program_name
LEFT JOIN Students s ON m.program_name = s.program_name
LEFT JOIN enrollment e ON s.student_id = e.student_id
LEFT JOIN lecturers l ON m.lecturer_id = l.lecturer_id
LEFT JOIN rooms r ON m.room_id = r.room_id
GROUP BY p.program_name, m.module_name, l.lecturer_name, r.room_name;

-- Release the locks
UNLOCK TABLES;

# student report
-- Lock the tables being read
LOCK TABLES enrollment e READ, Students s READ, program p READ, Modules m READ, grades g READ;

SELECT s.student_id, s.student_name, p.program_name, m.module_name, g.grades, e.finishing_date,
CASE WHEN g.grades < 40 THEN 'Y' ELSE 'N' END AS needs_to_repeat_class
FROM enrollment e
JOIN Students s ON e.student_id = s.student_id
JOIN program p ON s.program_name = p.program_name
JOIN Modules m ON p.program_name = m.program_name
LEFT JOIN grades g ON e.student_id = g.student_id AND m.module_id = g.module_id
WHERE g.grades IS NOT NULL
ORDER BY s.student_id, m.module_name;

-- Release the locks
UNLOCK TABLES;


#Lecturer report
-- Lock the tables being read
LOCK TABLES enrollment e READ, Students s READ, program p READ, Modules m READ, lecturers l READ;

SELECT l.lecturer_name, l.job_role, m.module_name, COUNT(e.student_id) AS num_students_currently, l.teaching_classes AS type_class_they_can_teach
FROM enrollment e
JOIN Students s ON e.student_id = s.student_id
JOIN program p ON s.program_name = p.program_name
JOIN Modules m ON p.program_name = m.program_name
JOIN lecturers l ON m.module_id = l.module_id
WHERE e.finishing_date > '2024-03-21'
GROUP BY l.lecturer_name, l.job_role, m.module_name, l.teaching_classes
ORDER BY l.lecturer_name ASC;

-- Release the locks
UNLOCK TABLES;