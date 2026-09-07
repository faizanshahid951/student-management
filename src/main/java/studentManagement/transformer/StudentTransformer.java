package studentManagement.transformer;

import studentManagement.domain.StudentDomain;
import studentManagement.dto.StudentCreateDTO;
import lombok.Builder;
import org.springframework.stereotype.Component;
import studentManagement.dto.StudentResponseDTO;

@Component
@Builder
public class StudentTransformer {
    public StudentDomain toStudentDomain(StudentCreateDTO studentDTO){
        return StudentDomain.builder()
                .firstname(studentDTO.getFirstname())
                .lastname(studentDTO.getLastname())
                .email(studentDTO.getEmail())
                .age(studentDTO.getAge())
                .course(studentDTO.getCourse())
                .semester(studentDTO.getSemester())
                .cgpa(studentDTO.getCgpa())
                .build();
    }


    public StudentResponseDTO toStudentDTO(StudentDomain studentDomain){
        return StudentResponseDTO.builder()
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
