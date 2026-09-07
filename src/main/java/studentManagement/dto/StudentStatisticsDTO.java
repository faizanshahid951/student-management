package studentManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentStatisticsDTO {

    private long totalStudents;
    private double averageCgpa;
    private double highestCgpa;
    private double lowestCgpa;
    private long excellentStudents;
    private long atRiskStudents;
}