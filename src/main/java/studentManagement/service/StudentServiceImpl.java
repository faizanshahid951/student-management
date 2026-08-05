package studentManagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import studentManagement.domain.StudentDomain;
import studentManagement.dto.StudentDTO;
import studentManagement.repo.StudentRepo;
import studentManagement.transformer.StudentTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepo;
    private final StudentTransformer studentTransformer;

    @Override
    public StudentDTO saveStudent(StudentDTO studentDTO){
       StudentDomain domain = studentTransformer.toStudentDomain(studentDTO);
        return  studentTransformer.toStudentDTO(studentRepo.save(domain));
    }

    @Override
    public Page<StudentDTO> getAllStudent(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<StudentDomain> students = studentRepo.findAll(pageable);
        return students.map(studentTransformer::toStudentDTO);
    }

    @Override
    public void deleteStudent(String id) {
        studentRepo.deleteById(id);
    }

    @Override
    public StudentDTO getStudentById(String id) {

        StudentDomain student = studentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Student not found with ID: " + id
                ));

        return studentTransformer.toStudentDTO(student);
    }
    @Override
    public StudentDTO updateStudent(String id, StudentDTO studentDTO) {

        StudentDomain existingStudent = studentRepo.findById(id)
          .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));

        existingStudent.setFirstname(studentDTO.getFirstname());
        existingStudent.setLastname(studentDTO.getLastname());
        existingStudent.setAge(studentDTO.getAge());
        existingStudent.setCourse(studentDTO.getCourse());
        existingStudent.setSemester(studentDTO.getSemester());
        existingStudent.setCgpa(studentDTO.getCgpa());

        StudentDomain updatedStudent = studentRepo.save(existingStudent);

        return studentTransformer.toStudentDTO(updatedStudent);
    }

    @Override
    public List<StudentDTO> getStudentByCourse(String course) {

        List<StudentDomain> students = studentRepo.findByCourseIgnoreCase(course);

        return students.stream()
                .map(studentTransformer::toStudentDTO)
                .toList();
    }
    @Override
    public List<StudentDTO> getStudentByCgpa(double cgpa) {

        List<StudentDomain> students = studentRepo.findByCgpa(cgpa);

        return students.stream()
                .map(studentTransformer::toStudentDTO)
                .toList();
    }
    @Override
    public List<StudentDTO> findAllByOrderByCgpaDesc(){
        List<StudentDomain> students = studentRepo.findAllByOrderByCgpaDesc();

        return students.stream()
                .map(studentTransformer::toStudentDTO)
                .toList();
    }
}
