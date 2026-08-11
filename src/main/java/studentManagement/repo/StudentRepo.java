package studentManagement.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import studentManagement.domain.StudentDomain;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StudentRepo extends MongoRepository<StudentDomain, String> {

    Page<StudentDomain> findByActiveTrue(Pageable pageable);

    List<StudentDomain> findByCourseIgnoreCaseAndActiveTrue(String course);

    List<StudentDomain> findByCgpaAndActiveTrue(double cgpa);

    List<StudentDomain> findByActiveTrueOrderByCgpaDesc();

    boolean existsByEmailIgnoreCase(String email);
}
