# Academic System With Security Rest API
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white)  ![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white) ![SpringSecurity](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white) ![JWT](https://img.shields.io/badge/JWT-323330?style=for-the-badge&logo=json-web-tokens&logoColor=pink) 

I developed a Rest API to manage courses and their professors and students, in an academic system, built by using **Spring Boot, Java, and MySQL as the Database**, providing CRUD (Create, Read, Update, Delete) operations with authentication control enabled through **Spring Security and JWT tokens**. This API allows storing course information, such as: course code, name, description, credits, and max students (the maximum number of students allowed in the course). It also supports storing professor information, which includes: document, first name, last name, phone number, department, and email, as well as student information, which includes: document, first name, last name, gender, birth date, and email. Finally, to ensure security, storing user information such as username and password is necessary for all user types, including admin, student, and professor.

I used some libraries for this Rest API such **Spring Web, Spring Data JPA, Validation, ModelMapper, MySQL Driver, Spring Security and java-jwt**.

## Requirements

Below are some business rules that are essential for the system's functionality:

• The course code is unique in the system, therefore, only one registration with the same code course is allowed. Similarly, for both professors and students, the document and email must also be unique in the system. Additionally, the phone number must be unique for professors. All fields for both course and student are mandatory and must be filled out.

• A professor can teach multiple courses, while each course can be taught by only one professor. A course can have many students, and similarly, a student can be enrolled in many courses.

• In order for a student to enroll in a course, the system must check if the current students are less than the maximum students allowed. If so, the student can enroll in the course. Otherwise, the system should inform that there are no available spots in the course (the course is full).

• A student has a score for each enrolled course. The course teacher assigns the score after the student enrolls (the initial score is set to a default value of 0 once the student enrolls).

• Allow the creation and removal of a professor, course, or student from the system. Once a new user is created, a default password is provided to them.

• After a student is removed from the system, the system must update all the related courses to reflect that an additional spot is now available (update the number of current students for those courses).

• Allow students to remove themselves (or unenroll) from a particular course.

• Once a student removes themselves from a course, the system must update the course to reflect that an additional spot is now available.

• Allow students to enroll in courses.

• Allows students to retrieve the list of courses they are enrolled in. This search can also include additional detailed information, such as their scores in the courses.

• Allow professors to retrieve a list of all courses they teach.

• Allows professors to retrieve the list of students enrolled in the courses they teach. This search can also include additional detailed information, such as student scores.

• Allow professors to update student scores for their courses.


## Authentication
The API uses Spring Security for authentication control. The following roles are available:

```
PROFESSOR -> User role dedicated to professor-specific operations (e.g., retrieving courses, listing students enrolled in those courses, and updating student scores).
STUDENT -> User role dedicated to student-specific operations (e.g., enrolling and unenrolling in courses, and retrieving the list of courses they are enrolled in).
ADMIN -> Administrative role for operations such as creating new users, managing courses, and performing advanced actions (e.g., registrations, updates, or deletions).
```

## Database Initialization with Default Data
For this project, default users and courses have been created for quick testing using the scheme.sql and data.sql files. Additionally, endpoints are available for registering new users, logging in, and performing operations. Note that each new user is created with a default password. 

The default users were created with the following credentials:

- Username: marcos, Password: 123 (ADMIN role).

- Username: pedro, Password: 123 (PROFESSOR role).

- Username: leonardo, Password: 123 (PROFESSOR role).

- Username: andres, Password: 123 (STUDENT role).

- Username: maria, Password: 123 (STUDENT role).

## Database Config
For this API, the MySQL Database was used with the following configuration properties: 

- Database name: academic_system_with_security_db
- Username: root
- Password:

## Database Initialization Configuration
To run the application correctly, for the first time make sure the database already exists and set the following configuration in the application.properties file:

spring.sql.init.mode=always

For subsequent runs (once the database and its tables are already created), change the setting to:

spring.sql.init.mode=embedded

## Development Tools
This Rest API was built with:

- Spring Boot version: 3.3.4
- Java version: 17

## System Class Diagram

![AcademicSystemWithSecurityClassDiagram](https://github.com/user-attachments/assets/5b5d737b-4741-4d10-b528-528e17772a72)
