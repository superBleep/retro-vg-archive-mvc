package com.superbleep.rvgamvc.repositories;

import com.superbleep.rvgamvc.domain.Game;
import com.superbleep.rvgamvc.domain.GameVersionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long>, JpaSpecificationExecutor<Game> {
    @Query("""
        SELECT g
        FROM Game g
        JOIN g.platform p
        WHERE p.id = :id
    """)
    List<Game> findAllByPlatformId(Long id);

    @Query("""
        SELECT g
        FROM Game g
        JOIN g.gameVersions gv
        WHERE gv.id = :id
    """)
    Game findByGameVersionId(GameVersionId id);
}
