package com.superbleep.rvgamvc.repositories;

import com.superbleep.rvgamvc.domain.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlatformRepository extends JpaRepository<Platform, Long> {

    List<Platform> findByName(String name);
    List<Platform> findByManufacturer(String manufacturer);

    @Query("""
        SELECT p
        FROM Platform p
        WHERE YEAR(p.release) = ?1
    """)
    List<Platform> findByReleaseYear(Integer year);

    @Query("""
        SELECT p
        FROM Platform p
        JOIN p.emulators e
        WHERE e.id = :id
    """)
    List<Platform> findAllByEmulatorId(Long id);
}
