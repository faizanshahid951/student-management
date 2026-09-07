package studentManagement.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import studentManagement.domain.StudentDomain;
import studentManagement.dto.CourseStatisticsDTO;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CourseStatisticsRepo {

    private final MongoTemplate mongoTemplate;

    public List<CourseStatisticsDTO> getCourseStatistics() {

        Aggregation aggregation = Aggregation.newAggregation(

                Aggregation.match(
                        Criteria.where("active").is(true)
                ),

                Aggregation.group("course")
                        .count().as("totalStudents")
                        .avg("cgpa").as("averageCgpa"),

                Aggregation.project(
                                "totalStudents",
                                "averageCgpa"
                        )
                        .and("_id").as("course")
        );

        return mongoTemplate.aggregate(
                aggregation,
                StudentDomain.class,
                CourseStatisticsDTO.class
        ).getMappedResults();
    }
}