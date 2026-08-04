package studentManagement.repo;

import studentManagement.domain.StudentDomain;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepo extends MongoRepository<StudentDomain, String> {
}
