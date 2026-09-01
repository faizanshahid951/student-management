package studentManagement.transformer;

import studentManagement.domain.StudentDomain;
import studentManagement.dto.StudentDTO;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
@Builder
public class StudentTransformer {
    public StudentDomain toStudentDomain(StudentDTO studentDTO){
        return StudentDomain.builder()
                .id(studentDTO.getId())
                .firstname(studentDTO.getFirstname())
                .lastname(studentDTO.getLastname())
                .email(studentDTO.getEmail())
                .age(studentDTO.getAge())
                .course(studentDTO.getCourse())
                .semester(studentDTO.getSemester())
                .cgpa(studentDTO.getCgpa())
                .build();
    }


    public StudentDTO toStudentDTO(StudentDomain studentDomain){
        return StudentDTO.builder()
                .id(studentDomain.getId())
                .firstname(studentDomain.getFirstname())
                .lastname(studentDomain.getLastname())
                .email(studentDomain.getEmail())
                .age(studentDomain.getAge())
                .course(studentDomain.getCourse())
                .semester(studentDomain.getSemester())
                .cgpa(studentDomain.getCgpa())
                .academicStatus(studentDomain.getAcademicStatus())
                .scholarshipPercentage(studentDomain.getScholarshipPercentage())
                .academicProbation(studentDomain.isAcademicProbation())
                .requestAdvisor(studentDomain.isRequestAdvisor())
                .active(studentDomain.isActive())
                .build();
    }
}
