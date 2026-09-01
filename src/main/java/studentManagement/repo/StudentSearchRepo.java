package studentManagement.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import studentManagement.domain.StudentDomain;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StudentSearchRepo {

    private final MongoTemplate mongoTemplate;

    public Page<StudentDomain> searchStudents(
            String course,
            Double minCgpa,
            Double maxCgpa,
            Integer semester,
            int page,
            int size) {

        Query query = new Query();

        // Sirf active students
        query.addCriteria(
                Criteria.where("active").is(true)
        );

        // Course diya hai to course filter
        if (course != null && !course.isBlank()) {

            query.addCriteria(
                    Criteria.where("course")
                            .regex("^" + course + "$", "i")
            );
        }

        // CGPA filters
        if (minCgpa != null || maxCgpa != null) {

            Criteria cgpaCriteria =
                    Criteria.where("cgpa");

            if (minCgpa != null) {
                cgpaCriteria.gte(minCgpa);
            }

            if (maxCgpa != null) {
                cgpaCriteria.lte(maxCgpa);
            }

            query.addCriteria(cgpaCriteria);
        }

        // Semester diya hai to semester filter
        if (semester != null) {

            query.addCriteria(
                    Criteria.where("semester").is(semester)
            );
        }

        long total =
                mongoTemplate.count(query, StudentDomain.class);

        Pageable pageable =
                PageRequest.of(page, size);

        query.with(pageable);

        List<StudentDomain> students =
                mongoTemplate.find(
                        query,
                        StudentDomain.class
                );

        return new PageImpl<>(
                students,
                pageable,
                total
        );
    }
}