package com.superbleep.rvgamvc.services.emulator;

import com.superbleep.rvgamvc.domain.Emulator;
import com.superbleep.rvgamvc.domain.Platform;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EmulatorSpecifications {
    public static Specification<Emulator> filterByFields(String name, String developer, String platformName, Integer releaseYear) {
        return ((root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();

                if (name != null)
                    predicates.add(criteriaBuilder.equal(root.get("name"), name));
                if (developer != null)
                    predicates.add(criteriaBuilder.equal(root.get("developer"), developer));
                if (releaseYear != null) {
                    Expression<Integer> yearExpression = criteriaBuilder.function(
                            "year", Integer.class, root.get("release"));
                    predicates.add(criteriaBuilder.equal(yearExpression, releaseYear));
                }
                if (platformName != null) {
                    Join<Emulator, Platform> join = root.join("platforms");
                    predicates.add(criteriaBuilder.equal(join.get("name"), platformName));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        );
    }
}
