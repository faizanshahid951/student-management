package studentManagement.strategy.scholarship;

public interface ScholarshipStrategy {

    boolean supports(double cgpa);

    int calculateScholarship();
}