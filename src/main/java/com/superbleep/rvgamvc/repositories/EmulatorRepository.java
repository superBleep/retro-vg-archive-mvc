package com.superbleep.rvgamvc.repositories;

import com.superbleep.rvgamvc.domain.Emulator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmulatorRepository extends JpaRepository<Emulator, Long> {
    List<Emulator> findByName(String name);
    List<Emulator> findByDeveloper(String developer);

    @Query("""
        SELECT e
        FROM Emulator e
        WHERE YEAR(e.release) = ?1
    """)
    List<Emulator> findByReleaseYear(Integer year);

    @Query("""
        SELECT e
        FROM Emulator e
        JOIN e.platforms p
        WHERE p.name = ?1
    """)
    List<Emulator> findByPlatformName(String name);
}
