package studentManagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentResponseDTO {

    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private Integer age;
    private String course;
    private Integer semester;
    private Double cgpa;

    private String academicStatus;
    private Integer scholarshipPercentage;
    private Boolean academicProbation;
    private Boolean requestAdvisor;
    private Boolean active;
}
