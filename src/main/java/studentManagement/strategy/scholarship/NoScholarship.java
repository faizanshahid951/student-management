package studentManagement.strategy.scholarship;

import org.springframework.stereotype.Component;

@Component
public class NoScholarship
        implements ScholarshipStrategy {

    @Override
    public boolean supports(double cgpa) {

        return cgpa < 3.0;
    }

    @Override
    public int calculateScholarship() {

        return 0;
    }
}