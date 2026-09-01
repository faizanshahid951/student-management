package studentManagement.service;

import org.springframework.data.domain.Page;
import studentManagement.domain.StudentDomain;
import studentManagement.dto.StudentDTO;
import java.util.List;

public interface StudentService {

    StudentDTO saveStudent(StudentDTO studentDTO);

    Page<StudentDTO> getAllStudent(
            String course,
            Double minCgpa,
            Double maxCgpa,
            Integer semester,
            int page,
            int size
    );

    void deleteStudent(String id);

    StudentDTO getStudentById(String id);

    StudentDTO updateStudent(String id, StudentDTO studentDTO);

    List<StudentDTO> getStudentByCourse(String course);

    List<StudentDTO> getStudentByCgpa(double cgpa);

    List<StudentDTO> findAllByOrderByCgpaDesc();
}
