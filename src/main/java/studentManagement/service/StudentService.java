package studentManagement.service;

import studentManagement.domain.StudentDomain;
import studentManagement.dto.StudentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {

    StudentDTO saveStudent(StudentDTO studentDTO);

    List<StudentDomain> getAllStudent();

    void deleteStudent(String id);

    StudentDTO getStudentById(String id);

    StudentDTO updateStudent(String id, StudentDTO studentDTO);

    List<StudentDTO> getStudentByCourse(String course);
}
