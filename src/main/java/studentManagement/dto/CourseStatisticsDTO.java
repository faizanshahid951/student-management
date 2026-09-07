package studentManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseStatisticsDTO {

    private String course;
    private long totalStudents;
    private double averageCgpa;
}