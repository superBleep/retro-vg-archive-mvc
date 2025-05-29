package com.superbleep.rvgamvc.repositories;

import com.superbleep.rvgamvc.domain.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Long>, JpaSpecificationExecutor<Platform> {
    @Query("""
        SELECT p
        FROM Platform p
        JOIN p.emulators e
        WHERE e.id = :id
    """)
    List<Platform> findAllByEmulatorId(Long id);
}
