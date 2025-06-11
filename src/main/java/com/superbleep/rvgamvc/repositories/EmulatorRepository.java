package com.superbleep.rvgamvc.repositories;

import com.superbleep.rvgamvc.domain.Emulator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmulatorRepository extends JpaRepository<Emulator, Long>, JpaSpecificationExecutor<Emulator> {
    @Query("""
        SELECT e
        FROM Emulator e
        JOIN e.platforms p
        WHERE p.id = :id
    """)
    List<Emulator> findAllByPlatformId(Long id);
}
