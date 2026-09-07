package studentManagement.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class StudentCreateDTO {

    @NotBlank(message = "First name is required")
    private String firstname;

    @NotBlank(message = "Last name is required")
    private String lastname;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;

    @NotNull
    @Min(17)
    @Max(40)
    private Integer age;

    @NotBlank(message = "Course is required")
    private String course;

    @NotNull
    @Min(1)
    @Max(8)
    private Integer semester;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("4.0")
    private Double cgpa;
}