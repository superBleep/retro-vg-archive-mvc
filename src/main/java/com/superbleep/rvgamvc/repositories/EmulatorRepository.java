package com.superbleep.rvgamvc.repositories;

import com.superbleep.rvgamvc.domain.Emulator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmulatorRepository extends JpaRepository<Emulator, Long>, JpaSpecificationExecutor<Emulator> {
}
