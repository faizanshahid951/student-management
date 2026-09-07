package studentManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<String> handleStudentNotFound(
            StudentNotFoundException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }


    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<String> handleDuplicateEmail(
            DuplicateEmailException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }


    @ExceptionHandler(CourseCapacityExceededException.class)
    public ResponseEntity<String> handleCourseCapacity(
            CourseCapacityExceededException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }


    @ExceptionHandler(InvalidCourseException.class)
    public ResponseEntity<String> handleInvalidCourse(
            InvalidCourseException exception) {

        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());
    }


    @ExceptionHandler(InvalidSemesterException.class)
    public ResponseEntity<String> handleInvalidSemester(
            InvalidSemesterException exception) {

        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());
    }


    @ExceptionHandler(InvalidSemesterProgressionException.class)
    public ResponseEntity<String> handleSemesterProgression(
            InvalidSemesterProgressionException exception) {

        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return ResponseEntity
                .badRequest()
                .body(errors);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(
            Exception exception) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something went wrong");
    }
}