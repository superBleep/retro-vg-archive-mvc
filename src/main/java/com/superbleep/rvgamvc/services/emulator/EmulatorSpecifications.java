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
        return ((root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();

                if (name != null && !name.isBlank())
                    predicates.add(cb.like(cb.lower(root.get("name")), name.toLowerCase() + "%"));
                if (developer != null && !developer.isBlank())
                    predicates.add(cb.like(cb.lower(root.get("developer")), developer.toLowerCase() + "%"));
                if (releaseYear != null) {
                    Expression<Integer> yearExpression = cb.function(
                            "year", Integer.class, root.get("release"));
                    predicates.add(cb.equal(yearExpression, releaseYear));
                }
                if (platformName != null && !platformName.isBlank()) {
                    Join<Emulator, Platform> join = root.join("platforms");
                    predicates.add(cb.like(cb.lower(root.get("name")), platformName.toLowerCase() + "%"));
                }

                return cb.and(predicates.toArray(new Predicate[0]));
            }
        );
    }
}
