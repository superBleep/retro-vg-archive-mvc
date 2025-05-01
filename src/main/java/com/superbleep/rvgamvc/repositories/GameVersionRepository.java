package com.superbleep.rvgamvc.repositories;

import com.superbleep.rvgamvc.domain.GameVersion;
import com.superbleep.rvgamvc.domain.GameVersionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameVersionRepository extends JpaRepository<GameVersion, GameVersionId> {
    @Query("""
        SELECT gv
        FROM GameVersion gv
        JOIN gv.game g
        WHERE g.title = ?1
    """)
    List<GameVersion> findByGameTitle(String title);

    @Query("""
        SELECT gv
        FROM GameVersion gv
        JOIN gv.game g
        WHERE g.title = ?1 AND YEAR(gv.release) = ?2
    """)
    List<GameVersion> findByGameTitleAndReleaseYear(String title, Integer year);
}
