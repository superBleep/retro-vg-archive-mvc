package com.superbleep.rvgamvc.services.game;

import com.superbleep.rvgamvc.domain.Game;
import com.superbleep.rvgamvc.domain.Platform;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class GameSpecifications {
    public static Specification<Game> filterByFields(String title, String developer, String publisher, String genre, String platformName) {
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (title != null && !title.isBlank())
                predicates.add(cb.like(cb.lower(root.get("title")), title.toLowerCase() + "%"));
            if (developer != null && !developer.isBlank())
                predicates.add(cb.like(cb.lower(root.get("developer")), developer.toLowerCase() + "%"));
            if (publisher != null && !publisher.isBlank())
                predicates.add(cb.like(cb.lower(root.get("publisher")), publisher.toLowerCase() + "%"));
            if (genre != null && !genre.isBlank())
                predicates.add(cb.like(cb.lower(root.get("genre")), genre.toLowerCase() + "%"));
            if (platformName != null && !platformName.isBlank()) {
                Join<Game, Platform> join = root.join("platform");
                predicates.add(cb.like(cb.lower(join.get("name")), platformName.toLowerCase() + "%"));
                query.distinct(true);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }
        );
    }
}
