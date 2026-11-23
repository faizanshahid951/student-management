package studentManagement.strategy.scholarship;

import org.springframework.stereotype.Component;

@Component
public class TenPercentScholarship
        implements ScholarshipStrategy {

    @Override
    public boolean supports(double cgpa) {

        return cgpa >= 3.0 && cgpa < 3.5;
    }

    @Override
    public int calculateScholarship() {

        return 10;
    }
}