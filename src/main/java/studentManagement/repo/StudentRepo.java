package studentManagement.repo;

import studentManagement.domain.StudentDomain;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StudentRepo extends MongoRepository<StudentDomain, String> {

    List<StudentDomain> findByCourseIgnoreCase(String course);
    List<StudentDomain> findByCgpa(double cgpa);
}
