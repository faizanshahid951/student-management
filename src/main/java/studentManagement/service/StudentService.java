package studentManagement.service;

import org.springframework.data.domain.Page;
import studentManagement.dto.*;

import java.util.List;

public interface StudentService {

    StudentResponseDTO saveStudent(StudentCreateDTO studentDTO);

    Page<StudentResponseDTO> getAllStudent(
            String course,
            Double minCgpa,
            Double maxCgpa,
            Integer semester,
            int page,
            int size);

    void deleteStudent(String id);

    StudentResponseDTO getStudentById(String id);

    StudentResponseDTO updateStudent(String id, StudentUpdateDTO studentDTO);

    List<StudentResponseDTO> getStudentByCourse(String course);

    List<StudentResponseDTO> getStudentByCgpa(double cgpa);

    List<StudentResponseDTO> findAllByOrderByCgpaDesc();

    StudentStatisticsDTO getStudentStatistics();

    List<CourseStatisticsDTO> getCourseStatistics();
}
