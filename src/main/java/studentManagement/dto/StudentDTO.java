//package studentManagement.dto;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import jakarta.validation.constraints.*;
//import lombok.*;
//import org.springframework.data.annotation.Id;
//
//@Builder
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class StudentDTO {
//    @Id
//    private String id;
//
//    @NotBlank(message = "First name is required")
//    private String firstname;
//    @NotBlank(message = "Last name is required")
//    private String lastname;
//    @NotBlank(message = "email is required")
//    @Email
//    private String email;
//
//    @NotNull(message = "Age is required")
//    @Min(value = 17, message = "Age must be at least 17")
//    @Max(value = 40, message = "Age must not be greater than 40")
//    private Integer age;
//
//    @NotBlank(message = "Course is required")
//    private String course;
//    @NotNull(message = "Semester is required")
//    @Min(value = 1, message = "Semester must be at least 1")
//    @Max(value = 8, message = "Semester must not be greater than 8")
//    private Integer semester;
//
//    @NotNull(message = "CGPA is required")
//    @DecimalMin(value = "0.0", message = "CGPA cannot be below 0")
//    @DecimalMax(value = "4.0", message = "CGPA cannot be greater than 4")
//    private Double cgpa;
//
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    private String academicStatus;
//
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    private Integer scholarshipPercentage;
//
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    private Boolean academicProbation;
//
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    private Boolean requestAdvisor;
//
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    private Boolean active;
//}
