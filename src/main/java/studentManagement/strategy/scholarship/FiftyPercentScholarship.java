package studentManagement.strategy.scholarship;

import org.springframework.stereotype.Component;

@Component
public class FiftyPercentScholarship
        implements ScholarshipStrategy {

    @Override
    public boolean supports(double cgpa) {
        return cgpa >= 3.8;
    }

    @Override
    public int calculateScholarship() {
        return 50;
    }
}