package studentManagement.strategy.scholarship;

import org.springframework.stereotype.Component;

@Component
public class TwentyFivePercentScholarship implements ScholarshipStrategy{

    @Override
    public boolean supports(double cgpa){
        return cgpa >=3.5 && cgpa < 3.8;
    }

    @Override
    public int calculateScholarship() {

        return 25;
    }
}