package com.superbleep.rvgamvc.services.platform;

import com.superbleep.rvgamvc.domain.Emulator;
import com.superbleep.rvgamvc.domain.Platform;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PlatformSpecifications {
    public static Specification<Platform> filterByFields(String name, String manufacturer, Integer releaseYear) {
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isBlank())
                predicates.add(cb.like(cb.lower(root.get("name")), name.toLowerCase() + "%"));
            if (manufacturer != null && !manufacturer.isBlank())
                predicates.add(cb.like(cb.lower(root.get("manufacturer")), manufacturer.toLowerCase() + "%"));
            if (releaseYear != null) {
                Expression<Integer> yearExpression = cb.function(
                        "year", Integer.class, root.get("release"));
                predicates.add(cb.equal(yearExpression, releaseYear));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }
        );
    }
}
