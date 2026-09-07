package studentManagement.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import studentManagement.domain.StudentDomain;
import studentManagement.dto.StudentStatisticsDTO;

@Repository
@RequiredArgsConstructor
public class StudentStatisticsRepo {

    private final MongoTemplate mongoTemplate;

    public StudentStatisticsDTO getStatistics() {

        Aggregation aggregation = Aggregation.newAggregation(

                Aggregation.match(
                        Criteria.where("active").is(true)
                ),

                Aggregation.group()
                        .count().as("totalStudents")
                        .avg("cgpa").as("averageCgpa")
                        .max("cgpa").as("highestCgpa")
                        .min("cgpa").as("lowestCgpa")

                        .sum(
                                ConditionalOperators.when(
                                        Criteria.where("academicStatus")
                                                .is("EXCELLENT")
                                ).then(1).otherwise(0)
                        ).as("excellentStudents")

                        .sum(
                                ConditionalOperators.when(
                                        Criteria.where("academicStatus")
                                                .is("AT_RISK")
                                ).then(1).otherwise(0)
                        ).as("atRiskStudents")
        );

        StudentStatisticsDTO result =
                mongoTemplate.aggregate(
                        aggregation,
                        StudentDomain.class,
                        StudentStatisticsDTO.class
                ).getUniqueMappedResult();

        if (result == null) {
            return new StudentStatisticsDTO(
                    0, 0, 0, 0, 0, 0
            );
        }

        return result;
    }
}