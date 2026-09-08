package studentManagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import studentManagement.domain.StudentDomain;
import studentManagement.dto.StudentCreateDTO;
import studentManagement.dto.StudentResponseDTO;
import studentManagement.exception.CourseCapacityExceededException;
import studentManagement.exception.DuplicateEmailException;
import studentManagement.exception.InvalidCourseException;
import studentManagement.exception.StudentNotFoundException;
import studentManagement.repo.CourseStatisticsRepo;
import studentManagement.repo.StudentRepo;
import studentManagement.repo.StudentSearchRepo;
import studentManagement.repo.StudentStatisticsRepo;
import studentManagement.service.StudentServiceImpl;
import studentManagement.transformer.StudentTransformer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FourthApplicationTests {

    @Mock
    private StudentRepo studentRepo;
    @Mock
    private StudentSearchRepo studentSearchRepo;
    @Mock
    private StudentStatisticsRepo studentStatisticsRepo;
    @Mock
    private CourseStatisticsRepo courseStatisticsRepo;

    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentServiceImpl(studentRepo, StudentTransformer.builder().build(),
                studentSearchRepo, studentStatisticsRepo, courseStatisticsRepo);
    }

    @ParameterizedTest
    @CsvSource({
            "0.0, AT_RISK, 0, true",
            "1.99, AT_RISK, 0, true",
            "2.0, AVERAGE, 0, false",
            "2.99, AVERAGE, 0, false",
            "3.0, GOOD, 10, false",
            "3.49, GOOD, 10, false",
            "3.5, EXCELLENT, 25, false",
            "3.79, EXCELLENT, 25, false",
            "3.8, EXCELLENT, 50, false",
            "4.0, EXCELLENT, 50, false"
    })
    void saveStudentCalculatesAcademicBenefitsAndSupport(double cgpa, String status,
                                                       int scholarship, boolean needsSupport) {
        StudentCreateDTO request = studentRequest(cgpa, "Computer Science");
        when(studentRepo.save(any(StudentDomain.class))).thenAnswer(invocation -> {
            StudentDomain student = invocation.getArgument(0);
            student.setId("student-1");
            return student;
        });

        StudentResponseDTO response = studentService.saveStudent(request);

        assertAll(
                () -> assertEquals("student-1", response.getId()),
                () -> assertEquals(request.getFirstname(), response.getFirstname()),
                () -> assertEquals(request.getLastname(), response.getLastname()),
                () -> assertEquals(request.getEmail(), response.getEmail()),
                () -> assertEquals(request.getAge(), response.getAge()),
                () -> assertEquals(request.getCourse(), response.getCourse()),
                () -> assertEquals(request.getSemester(), response.getSemester()),
                () -> assertEquals(cgpa, response.getCgpa()),
                () -> assertEquals(status, response.getAcademicStatus()),
                () -> assertEquals(scholarship, response.getScholarshipPercentage()),
                () -> assertEquals(needsSupport, response.getAcademicProbation()),
                () -> assertEquals(needsSupport, response.getRequestAdvisor()),
                () -> assertTrue(response.getActive())
        );
        verify(studentRepo).save(any(StudentDomain.class));
    }

    @Test
    void saveStudentRejectsDuplicateEmailBeforeSaving() {
        StudentCreateDTO request = studentRequest(3.0, "math");
        when(studentRepo.existsByEmailIgnoreCase(request.getEmail())).thenReturn(true);

        assertThrows(DuplicateEmailException.class, () -> studentService.saveStudent(request));

        verify(studentRepo, never()).save(any());
        verify(studentRepo, never()).countByCourseIgnoreCaseAndActiveTrue(any());
    }

    @ParameterizedTest
    @CsvSource({"Computer Science, 100", "MATH, 80", "se, 60"})
    void saveStudentRejectsFullCourse(String course, int capacity) {
        when(studentRepo.countByCourseIgnoreCaseAndActiveTrue(course)).thenReturn(capacity);

        assertThrows(CourseCapacityExceededException.class,
                () -> studentService.saveStudent(studentRequest(3.0, course)));

        verify(studentRepo, never()).save(any());
    }

    @ParameterizedTest
    @CsvSource({"Computer Science, 99", "MATH, 79", "se, 59"})
    void saveStudentAcceptsLastAvailablePlace(String course, int currentStudents) {
        when(studentRepo.countByCourseIgnoreCaseAndActiveTrue(course)).thenReturn(currentStudents);
        when(studentRepo.save(any(StudentDomain.class))).thenAnswer(invocation -> invocation.getArgument(0));

        StudentResponseDTO response = studentService.saveStudent(studentRequest(3.0, course));

        assertEquals(course, response.getCourse());
        assertTrue(response.getActive());
        verify(studentRepo).save(any(StudentDomain.class));
    }

    @Test
    void saveStudentRejectsUnknownCourse() {
        assertThrows(InvalidCourseException.class,
                () -> studentService.saveStudent(studentRequest(3.0, "unknown")));

        verify(studentRepo, never()).save(any());
    }

    @Test
    void getStudentByIdReturnsStoredStudent() {
        StudentDomain student = StudentDomain.builder().id("student-1")
                .firstname("Ali").email("ali@example.com").active(true).build();
        when(studentRepo.findById("student-1")).thenReturn(Optional.of(student));

        StudentResponseDTO response = studentService.getStudentById("student-1");

        assertEquals(student.getId(), response.getId());
        assertEquals(student.getFirstname(), response.getFirstname());
        assertEquals(student.getEmail(), response.getEmail());
        assertTrue(response.getActive());
    }

    @Test
    void getStudentByIdRejectsMissingStudent() {
        when(studentRepo.findById("missing")).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.getStudentById("missing"));
    }

    @Test
    void deleteStudentDeactivatesStoredStudent() {
        StudentDomain student = StudentDomain.builder().id("student-1").active(true).build();
        when(studentRepo.findById("student-1")).thenReturn(Optional.of(student));

        studentService.deleteStudent("student-1");

        assertFalse(student.isActive());
        verify(studentRepo).save(student);
        verify(studentRepo, never()).deleteById(any());
    }

    @Test
    void deleteStudentRejectsMissingStudentWithoutSaving() {
        when(studentRepo.findById("missing")).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.deleteStudent("missing"));

        verify(studentRepo, never()).save(any());
    }

    private StudentCreateDTO studentRequest(double cgpa, String course) {
        StudentCreateDTO request = new StudentCreateDTO();
        request.setFirstname("Ali");
        request.setLastname("Khan");
        request.setEmail("ali@example.com");
        request.setAge(20);
        request.setCourse(course);
        request.setSemester(2);
        request.setCgpa(cgpa);
        return request;
    }
}
