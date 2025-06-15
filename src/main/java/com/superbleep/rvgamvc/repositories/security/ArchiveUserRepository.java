package com.superbleep.rvgamvc.repositories.security;

import com.superbleep.rvgamvc.domain.security.ArchiveUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArchiveUserRepository extends JpaRepository<ArchiveUser, Integer> {
    Optional<ArchiveUser> findByUsername(String username);
}
