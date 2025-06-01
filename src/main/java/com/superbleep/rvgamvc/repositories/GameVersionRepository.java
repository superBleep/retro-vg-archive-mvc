package com.superbleep.rvgamvc.repositories;

import com.superbleep.rvgamvc.domain.GameVersion;
import com.superbleep.rvgamvc.domain.GameVersionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameVersionRepository extends JpaRepository<GameVersion, GameVersionId> {
    @Query("""
        SELECT g
        FROM GameVersion gv
        JOIN gv.game g
        WHERE g.id = :id
    """)
    List<GameVersion> findAllByGameId(Long id);
}
