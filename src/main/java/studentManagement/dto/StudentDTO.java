package studentManagement.dto;

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

    private String lastname;

    @NotNull(message = "Age is required")
    @Min(17)
    @Max(value = 40, message = "Age must not be greater than 40")
    private Integer age;

    @NotBlank(message = "Course is required")
    private String course;

    private Integer semester;

    @NotNull(message = "CGPA is required")
    @DecimalMin(value = "0.0", message = "CGPA cannot be below 0")
    @DecimalMax(value = "4.0", message = "CGPA cannot be greater than 4")
    private Double cgpa;
}
