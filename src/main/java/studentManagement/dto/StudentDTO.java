package studentManagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.Id;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    @Id
    private String id;

    @NotBlank(message = "First name is required")
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank(message = "email is required")
    @Email
    private String email;

    @NotNull(message = "Age is required")
    @Min(17)
    @Max(value = 40, message = "Age must not be greater than 40")
    private int age;

    @NotBlank(message = "Course is required")
    private String course;
    @NotNull
    private int semester;

    @NotNull(message = "CGPA is required")
    @DecimalMin(value = "0.0", message = "CGPA cannot be below 0")
    @DecimalMax(value = "4.0", message = "CGPA cannot be greater than 4")
    private Double cgpa;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String academicStatus;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer scholarshipPercentage;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean academicProbation;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean requestAdvisor;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean active;
}
