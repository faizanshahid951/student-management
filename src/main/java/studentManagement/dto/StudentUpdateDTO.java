package studentManagement.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class StudentUpdateDTO {

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @Email
    @NotBlank
    private String email;

    @NotNull
    @Min(17)
    @Max(40)
    private Integer age;

    @NotBlank
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
