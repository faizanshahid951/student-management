# student_management
# Student Management API

A Spring Boot REST API for managing student records using MongoDB.

## Base URL

```text
http://localhost:8080/students
```

---

## 1. Get All Students

Returns all students stored in the database.

### Request

```http
GET /students
```

### Postman URL

```text
http://localhost:8080/students
```

### Example Response

```json
[
  {
    "id": "111",
    "firstname": "Ali",
    "lastname": "Khan",
    "age": 22,
    "course": "Computer Science",
    "semester": 6,
    "cgpa": 3.75
  }
]
```

---

## 2. Create a Student

Creates a new student record.

### Request

```http
POST /students
```

### Postman URL

```text
http://localhost:8080/students
```

### Request Body

Select **Body → raw → JSON** in Postman.

```json
{
  "id": "111",
  "firstname": "Ali",
  "lastname": "Khan",
  "age": 22,
  "course": "Computer Science",
  "semester": 6,
  "cgpa": 3.75
}
```

### Validation Rules

* `firstname` is required.
* `age` must be between `17` and `40`.
* `course` is required.
* `cgpa` must be between `0.0` and `4.0`.

### Example Response

```json
{
  "id": "111",
  "firstname": "Ali",
  "lastname": "Khan",
  "age": 22,
  "course": "Computer Science",
  "semester": 6,
  "cgpa": 3.75
}
```

> Using an existing ID in a POST request may update the existing MongoDB document. To let MongoDB generate an ID automatically, do not include the `id` field.

---

## 3. Get a Student by ID

Returns one student using the student's ID.

### Request

```http
GET /students/{id}
```

### Postman Example

```text
http://localhost:8080/students/111
```

### Example Response

```json
{
  "id": "111",
  "firstname": "Ali",
  "lastname": "Khan",
  "age": 22,
  "course": "Computer Science",
  "semester": 6,
  "cgpa": 3.75
}
```

---

## 4. Update a Student

Updates an existing student using the student's ID.

### Request

```http
PUT /students/{id}
```

### Postman Example

```text
http://localhost:8080/students/111
```

### Request Body

```json
{
  "firstname": "Ali Updated",
  "lastname": "Khan",
  "age": 23,
  "course": "Software Engineering",
  "semester": 7,
  "cgpa": 3.9
}
```

The ID does not need to be included in the request body because it is taken from the URL.

### Example Response

```json
{
  "id": "111",
  "firstname": "Ali Updated",
  "lastname": "Khan",
  "age": 23,
  "course": "Software Engineering",
  "semester": 7,
  "cgpa": 3.9
}
```

---

## 5. Delete a Student

Deletes a student using the student's ID.

### Request

```http
DELETE /students/{id}
```

### Postman Example

```text
http://localhost:8080/students/111
```

No request body is required.

### Example Response

```text
Student deleted successfully
```

---

## 6. Search Students by Course

Returns all students enrolled in the specified course.

### Request

```http
GET /students/course/{course}
```

### Postman Example

```text
http://localhost:8080/students/course/Computer%20Science
```

`%20` represents a space in the URL.

The search ignores uppercase and lowercase differences.

For example, these values can be treated as the same course:

```text
Computer Science
computer science
COMPUTER SCIENCE
```

### Example Response

```json
[
  {
    "id": "111",
    "firstname": "Ali",
    "lastname": "Khan",
    "age": 22,
    "course": "Computer Science",
    "semester": 6,
    "cgpa": 3.75
  },
  {
    "id": "112",
    "firstname": "Sara",
    "lastname": "Ahmed",
    "age": 21,
    "course": "Computer Science",
    "semester": 5,
    "cgpa": 3.6
  }
]
```

---

## 7. Search Students by CGPA

Returns all students with the specified CGPA.

### Request

```http
GET /students/cgpa/{cgpa}
```

### Postman Example

```text
http://localhost:8080/students/cgpa/3.75
```

This endpoint performs an exact CGPA search.

### Example Response

```json
[
  {
    "id": "111",
    "firstname": "Ali",
    "lastname": "Khan",
    "age": 22,
    "course": "Computer Science",
    "semester": 6,
    "cgpa": 3.75
  }
]
```

If no student has the specified CGPA, an empty list is returned:

```json
[]
```

---

## 8. Get Students Sorted by CGPA

Returns all students sorted by CGPA in descending order.

The student with the highest CGPA appears first.

### Request

```http
GET /students/sorted
```

### Postman URL

```text
http://localhost:8080/students/sorted
```

### Example Response

```json
[
  {
    "id": "101",
    "firstname": "Ali",
    "lastname": "Khan",
    "age": 22,
    "course": "Computer Science",
    "semester": 6,
    "cgpa": 3.95
  },
  {
    "id": "102",
    "firstname": "Sara",
    "lastname": "Ahmed",
    "age": 21,
    "course": "Software Engineering",
    "semester": 5,
    "cgpa": 3.75
  },
  {
    "id": "103",
    "firstname": "Ahmed",
    "lastname": "Raza",
    "age": 23,
    "course": "Computer Science",
    "semester": 7,
    "cgpa": 3.2
  }
]
```

---

## API Summary

| HTTP Method | Endpoint                    | Description                      |
| ----------- | --------------------------- | -------------------------------- |
| GET         | `/students`                 | Get all students                 |
| POST        | `/students`                 | Create a new student             |
| GET         | `/students/{id}`            | Get a student by ID              |
| PUT         | `/students/{id}`            | Update a student                 |
| DELETE      | `/students/{id}`            | Delete a student                 |
| GET         | `/students/course/{course}` | Search students by course        |
| GET         | `/students/cgpa/{cgpa}`     | Search students by CGPA          |
| GET         | `/students/sorted`          | Sort students by CGPA descending |

## Technologies Used

* Java
* Spring Boot
* Spring Web
* Spring Data MongoDB
* MongoDB
* Lombok
* Jakarta Bean Validation
* Maven
