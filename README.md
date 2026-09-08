# Student Management System

## Project Purpose

This is a Spring Boot based Student Management System using MongoDB.

The application allows users to create, update, retrieve, search, sort,
paginate, and soft-delete students.

It also performs business processing such as:

- Academic status calculation
- Scholarship calculation
- Academic risk detection
- Course capacity checking
- Semester progression validation
- Duplicate email checking
- Student statistics
- Course-wise statistics


## Technologies Used

- Java
- Spring Boot
- Spring Data MongoDB
- MongoDB
- Maven
- Lombok
- Jakarta Validation
- Postman


## MongoDB Setup

1. Install and start MongoDB.
2. MongoDB should be running on:

mongodb://localhost:27017

3. Create/use a database for the project.

Example:

student_management

4. Student documents are stored in the students collection.


## Application Configuration

Configure MongoDB in:

src/main/resources/application.properties

Example:

spring.data.mongodb.uri=mongodb://localhost:27017/student_management


## How to Run the Application

1. Start MongoDB.
2. Open the project in IntelliJ IDEA.
3. Run the main Spring Boot application class.
4. The application will start on:

http://localhost:8080


## API Testing

The APIs can be tested using Postman.

Base URL:

http://localhost:8080/students


# APIs


## Create Student

POST /students

Example request:

{
"firstname": "Ali",
"lastname": "Khan",
"email": "ali@gmail.com",
"age": 22,
"course": "Computer Science",
"semester": 3,
"cgpa": 3.7
}


## Get All Students

GET /students

Returns active students.


## Get Student By ID

GET /students/{id}

Example:

GET /students/123


## Update Student

PUT /students/{id}

Example:

PUT /students/123


## Delete Student

DELETE /students/{id}

The student is soft deleted.

The MongoDB document remains in the database but:

active = false


## Search By Course

GET /students/course/{course}

Example:

GET /students/course/Computer%20Science


## Search By CGPA

GET /students/cgpa/{cgpa}

Example:

GET /students/cgpa/3.5


## Sorted Students

GET /students/sorted

Returns active students sorted by CGPA in descending order.

Highest CGPA appears first.


## Pagination

GET /students?page=0&size=5

page = page number

size = number of students per page


## Advanced Student Search

Optional filters:

- course
- minCgpa
- maxCgpa
- semester

Example:

GET /students?course=Computer%20Science

GET /students?minCgpa=3.0

GET /students?minCgpa=3.0&maxCgpa=3.8

GET /students?semester=5

Filters can also be combined with pagination.

Example:

GET /students?course=Computer%20Science&minCgpa=3.0&semester=5&page=0&size=10


## Student Statistics

GET /students/statistics

Returns:

- Total active students
- Average CGPA
- Highest CGPA
- Lowest CGPA
- Number of EXCELLENT students
- Number of AT_RISK students


## Course Statistics

GET /students/statistics/course

Returns course-wise:

- Course name
- Number of active students
- Average CGPA

MongoDB aggregation is used for this operation.


# Business Rules


## Duplicate Email Rule

Every student must have a unique email address.

A student cannot be created if another student already uses the same email.

When updating an email, the new email must not belong to another student.


## Academic Status Rules

Academic status is automatically calculated from CGPA.

CGPA >= 3.5
EXCELLENT

CGPA >= 3.0 and < 3.5
GOOD

CGPA >= 2.0 and < 3.0
AVERAGE

CGPA < 2.0
AT_RISK

The client does not set academic status manually.


## Scholarship Rules

Scholarship percentage is automatically calculated.

CGPA >= 3.8
50%

CGPA >= 3.5 and < 3.8
25%

CGPA >= 3.0 and < 3.5
10%

CGPA < 3.0
0%


## Course Capacity Rules

Only active students consume course capacity.

Computer Science:
Maximum 100 active students

Software Engineering:
Maximum 80 active students

Data Science:
Maximum 60 active students

A new student cannot be registered if the course is full.


## Academic Risk Rules

A student is considered academically at risk when:

CGPA < 2.0

For an at-risk student:

academicStatus = AT_RISK

academicProbation = true

requestAdvisor = true


## Semester Progression Rules

Supported semesters are:

1 to 8

A student:

- Cannot move to a previous semester.
- Cannot skip semesters.
- Can remain in the same semester.
- Can move only to the next semester.
- Must have CGPA >= 2.0 to progress to the next semester.

Example:

Current semester = 3

Semester 3 -> 4
Allowed if CGPA >= 2.0

Semester 3 -> 5
Not allowed because semesters cannot be skipped.

Semester 3 -> 2
Not allowed because backward progression is not allowed.


## Soft Delete Behavior

DELETE /students/{id}

does not physically remove the student from MongoDB.

Instead:

active = false

The student document remains in MongoDB.

Inactive students are excluded from:

- Normal GET requests
- Search results
- Pagination
- Sorting
- Student statistics
- Course statistics
- Course capacity counts


# Validation Rules

Student data is validated before saving.

- First name must not be empty.
- Last name must not be empty.
- Email must be valid.
- Age must be between 17 and 40.
- CGPA must be between 0 and 4.
- Course must not be empty.
- Semester must be between 1 and 8.


# Exception Handling

Centralized exception handling is implemented using:

@ControllerAdvice

The application handles errors such as:

- Student not found
- Duplicate email
- Course capacity exceeded
- Invalid course
- Invalid age
- Invalid CGPA
- Invalid semester
- Invalid semester progression

Internal stack traces and implementation details are not returned to API consumers.


# MongoDB Processing

MongoDB is used directly for:

- Filtering
- Counting
- Sorting
- Pagination
- Statistics
- Aggregation
- Course grouping

The application avoids loading the entire student collection into Java
when MongoDB can perform the operation directly.