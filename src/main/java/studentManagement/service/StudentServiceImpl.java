package studentManagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import studentManagement.domain.StudentDomain;
import studentManagement.dto.StudentDTO;
import studentManagement.exception.DuplicateEmailException;
import studentManagement.exception.StudentNotFoundException;
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
        boolean emailExists = studentRepo.existsByEmailIgnoreCase(studentDTO.getEmail());
        if (emailExists) {
            throw new DuplicateEmailException(
                    "Student with this email already exists"
            );
        }
        checkCourseCapacity(studentDTO.getCourse());
       StudentDomain domain = studentTransformer.toStudentDomain(studentDTO);
        domain.setAcademicStatus(calculateAcademicStatus(domain.getCgpa()));

        domain.setScholarshipPercentage(
                calculateScholarship(domain.getCgpa())
        );

        domain.setActive(true);
        return  studentTransformer.toStudentDTO(studentRepo.save(domain));
    }

    @Override
    public Page<StudentDTO> getAllStudent(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<StudentDomain> students = studentRepo.findByActiveTrue(pageable);
        return students.map(studentTransformer::toStudentDTO);
    }

    @Override
    public void deleteStudent(String id) {

        StudentDomain student = studentRepo.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found with ID: " + id
                        ));
        student.setActive(false);
        studentRepo.delete(student);
    }
    @Override
    public StudentDTO getStudentById(String id) {

        StudentDomain student = studentRepo.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(
                        "Student not found with ID: " + id
                ));

        return studentTransformer.toStudentDTO(student);
    }
    @Override
    public StudentDTO updateStudent(String id, StudentDTO studentDTO) {

        StudentDomain existingStudent = studentRepo.findById(id)
          .orElseThrow(() -> new StudentNotFoundException(
                  "Student not found with ID: " + id));

        existingStudent.setFirstname(studentDTO.getFirstname());
        existingStudent.setLastname(studentDTO.getLastname());
        existingStudent.setAge(studentDTO.getAge());
        existingStudent.setCourse(studentDTO.getCourse());
        existingStudent.setSemester(studentDTO.getSemester());
        existingStudent.setCgpa(studentDTO.getCgpa());

        existingStudent.setAcademicStatus(
                calculateAcademicStatus(studentDTO.getCgpa())
        );

        existingStudent.setScholarshipPercentage(
                calculateScholarship(studentDTO.getCgpa())
        );

        StudentDomain updatedStudent = studentRepo.save(existingStudent);

        return studentTransformer.toStudentDTO(updatedStudent);
    }

    @Override
    public List<StudentDTO> getStudentByCourse(String course) {

        List<StudentDomain> students = studentRepo.findByCourseIgnoreCaseAndActiveTrue(course);

        return students.stream()
                .map(studentTransformer::toStudentDTO)
                .toList();
    }
    @Override
    public List<StudentDTO> getStudentByCgpa(double cgpa) {

        List<StudentDomain> students = studentRepo.findByCgpaAndActiveTrue(cgpa);

        return students.stream()
                .map(studentTransformer::toStudentDTO)
                .toList();
    }
    @Override
    public List<StudentDTO> findAllByOrderByCgpaDesc(){
        List<StudentDomain> students = studentRepo.findByActiveTrueOrderByCgpaDesc();

        return students.stream()
                .map(studentTransformer::toStudentDTO)
                .toList();
    }
    private String calculateAcademicStatus(double cgpa) {

        if (cgpa >= 3.5) {
            return "EXCELLENT";

        } else if (cgpa >= 3.0) {
            return "GOOD";

        } else if (cgpa >= 2.0) {
            return "AVERAGE";

        } else {
            return "AT_RISK";
        }
    }
    private int calculateScholarship(double cgpa) {

        if (cgpa >= 3.8) {
            return 50;

        } else if (cgpa >= 3.5) {
            return 25;

        } else if (cgpa >= 3.0) {
            return 10;

        } else {
            return 0;
        }
    }
    private void checkCourseCapacity(String course){
        int currentStudents = studentRepo.countByCourseIgnoreCaseAndActiveTrue(course);

        int capacity;

        if (course.equalsIgnoreCase("computer Science")){
            capacity =100;
        } else if (course.equalsIgnoreCase("math")) {
            capacity=80;
        } else if (course.equalsIgnoreCase("se")) {
            capacity=60;
        }else {
            throw new RuntimeException("invalid course" + course);
        }
        if(currentStudents >= capacity){
            throw new RuntimeException("course capacity exceeded for " + course);

        }
    }
}
