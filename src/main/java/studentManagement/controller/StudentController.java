package studentManagement.controller;

import studentManagement.domain.StudentDomain;
import studentManagement.dto.StudentDTO;
import studentManagement.service.StudentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/students")
@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentDomain>> getAllStudent(){
        return ResponseEntity.ok(studentService.getAllStudent());
    }

    @PostMapping
    public StudentDTO createStudent(@Valid @RequestBody StudentDTO studentDTO){
        return studentService.saveStudent(studentDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@NotBlank @PathVariable String id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(
            @PathVariable String id,
          @Valid @RequestBody StudentDTO studentDTO) {

        StudentDTO updatedStudent = studentService.updateStudent(id, studentDTO);

        return ResponseEntity.ok(updatedStudent);
    }
    @GetMapping("/course/{course}")
    public ResponseEntity<List<StudentDTO>> getStudentsByCourse(@PathVariable String course) {
        return ResponseEntity.ok(studentService.getStudentByCourse(course));
    }
    @GetMapping("/cgpa/{cgpa}")
    public ResponseEntity<List<StudentDTO>> getStudentsByCgpa(@PathVariable double cgpa) {
        return ResponseEntity.ok(studentService.getStudentByCgpa(cgpa));
    }
    @GetMapping("/sorted")
    public ResponseEntity<List<StudentDTO>> getAllStudentByOrderByCgpaDesc() {
        return ResponseEntity.ok(studentService.findAllByOrderByCgpaDesc());
    }
}
