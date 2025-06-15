package com.superbleep.rvgamvc.repositories.security;

import com.superbleep.rvgamvc.domain.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}