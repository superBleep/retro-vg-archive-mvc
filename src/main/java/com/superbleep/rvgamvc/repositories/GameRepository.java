package com.superbleep.rvgamvc.repositories;

import com.superbleep.rvgamvc.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByTitle(String title);
    List<Game> findByDeveloper(String developer);
    List<Game> findByPublisher(String publisher);
    List<Game> findByGenre(String genre);

    @Query("""
        SELECT g
        FROM Game g
        JOIN g.platform p
        WHERE p.name = ?1
    """)
    List<Game> findByPlatformName(String name);
}
