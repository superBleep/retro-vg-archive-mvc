package com.superbleep.rvgamvc.services.security;

import com.superbleep.rvgamvc.domain.security.ArchiveUser;
import com.superbleep.rvgamvc.domain.security.Authority;
import com.superbleep.rvgamvc.repositories.security.ArchiveUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Profile("postgres")
public class JpaUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

    private final ArchiveUserRepository archiveUserRepository;

    public JpaUserDetailsService(ArchiveUserRepository archiveUserRepository) {
        this.archiveUserRepository = archiveUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws RuntimeException {
        ArchiveUser archiveUser;
        Optional<ArchiveUser> userOptional = archiveUserRepository.findByUsername(username);

        if (userOptional.isPresent())
            archiveUser = userOptional.get();
        else
            throw new RuntimeException("Username " + username + " not found!");

        logger.info(archiveUser.getUsername() + " logged in");
        logger.info(getAuthorities(archiveUser.getAuthorities()).toString());

        return new org.springframework.security.core.userdetails.User(
            archiveUser.getUsername(),
            archiveUser.getPassword(),
            getAuthorities(archiveUser.getAuthorities())
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<Authority> authorities) {
        if (authorities == null || authorities.isEmpty())
            return new HashSet<>();
        else
            return authorities.stream()
                    .map(Authority::getRole)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
    }
}
