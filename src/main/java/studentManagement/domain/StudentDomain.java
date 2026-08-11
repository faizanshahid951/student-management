package studentManagement.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "student_management")
public class StudentDomain {

    @Id
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private int age;
    private String course;
    private int semester;
    private double cgpa;
    private String academicStatus;
    private int scholarshipPercentage;
    private boolean active;

}
