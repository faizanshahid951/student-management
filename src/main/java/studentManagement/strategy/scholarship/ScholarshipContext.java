package studentManagement.strategy.scholarship;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ScholarshipContext {

    private final List<ScholarshipStrategy> strategies;

    public int calculate(double cgpa) {

        return strategies.stream()

                .filter(strategy ->
                        strategy.supports(cgpa))

                .findFirst()

                .orElseThrow(() ->
                        new RuntimeException(
                                "No scholarship strategy found"
                        ))

                .calculateScholarship();
    }
}