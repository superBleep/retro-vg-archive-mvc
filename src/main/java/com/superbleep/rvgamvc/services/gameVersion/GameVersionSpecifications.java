package com.superbleep.rvgamvc.services.gameVersion;

import com.superbleep.rvgamvc.domain.Game;
import com.superbleep.rvgamvc.domain.GameVersion;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class GameVersionSpecifications {
    public static Specification<GameVersion> filterByFields(String gameTitle, String version, Integer releaseYear, String notes, String dbms) {
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (gameTitle != null && !gameTitle.isBlank()) {
                Join<GameVersion, Game> join = root.join("game");
                predicates.add(cb.like(cb.lower(join.get("title")), gameTitle.toLowerCase() + "%"));
                query.distinct(true);
            }
            if (version != null && !version.isBlank())
                predicates.add(cb.like(cb.lower(root.get("id").get("id")), version.toLowerCase() + "%"));
            if (releaseYear != null) {
                Expression<?> yearExpression;

                if ("postgres".equalsIgnoreCase(dbms)) {
                    Expression<Double> yearExpr = cb.function(
                            "date_part", Double.class, cb.literal("year"), root.get("release")
                    );
                    predicates.add(cb.equal(yearExpr, releaseYear.doubleValue()));
                } else {
                    yearExpression = cb.function("year", Integer.class, root.get("release"));
                    predicates.add(cb.equal(yearExpression, releaseYear));
                }
            }
            if (notes != null && !notes.isBlank())
                predicates.add(cb.like(cb.lower(root.get("notes")), notes.toLowerCase() + "%"));

            return cb.and(predicates.toArray(new Predicate[0]));
        }
        );
    }
}
