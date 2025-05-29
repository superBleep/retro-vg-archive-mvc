package com.superbleep.rvgamvc.repositories;

import com.superbleep.rvgamvc.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("""
        SELECT g
        FROM Game g
        JOIN g.platform p
        WHERE p.id = :id
    """)
    List<Game> findAllByPlatformId(Long id);
}
