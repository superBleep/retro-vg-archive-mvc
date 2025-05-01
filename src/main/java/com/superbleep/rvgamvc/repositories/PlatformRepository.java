package com.superbleep.rvgamvc.repositories;

import com.superbleep.rvgamvc.domain.Emulator;
import com.superbleep.rvgamvc.domain.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlatformRepository extends JpaRepository<Platform, Long> {

    List<Emulator> findByName(String name);
    List<Emulator> findByManufacturer(String manufacturer);

    @Query("""
        SELECT p
        FROM Platform p
        WHERE YEAR(p.release) = ?1
    """)
    List<Emulator> findByReleaseYear(Integer year);
}
