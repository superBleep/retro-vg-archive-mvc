package com.superbleep.rvgamvc.bootstrap;

import com.superbleep.rvgamvc.domain.security.ArchiveUser;
import com.superbleep.rvgamvc.domain.security.Authority;
import com.superbleep.rvgamvc.repositories.security.AuthorityRepository;
import com.superbleep.rvgamvc.repositories.security.ArchiveUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("postgres")
public class ArchiveUserDataLoader implements CommandLineRunner {
    private AuthorityRepository authorityRepository;
    private ArchiveUserRepository archiveUserRepository;
    private PasswordEncoder passwordEncoder;

    public ArchiveUserDataLoader(AuthorityRepository authorityRepository, ArchiveUserRepository archiveUserRepository,
                                 PasswordEncoder passwordEncoder) {
        this.authorityRepository = authorityRepository;
        this.archiveUserRepository = archiveUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private void loadUserData() {
        if (archiveUserRepository.count() == 0) {
            Authority regularRole = authorityRepository.save(Authority.builder().role("ROLE_REGULAR").build());
            Authority moderatorRole = authorityRepository.save(Authority.builder().role("ROLE_MODERATOR").build());

            ArchiveUser regular = ArchiveUser.builder()
                    .username("xX_basicUser_Xx")
                    .password(passwordEncoder.encode("password"))
                    .authority(regularRole)
                    .build();

            ArchiveUser moderator = ArchiveUser.builder()
                    .username("mod")
                    .password(passwordEncoder.encode("password"))
                    .authority(moderatorRole)
                    .build();

            archiveUserRepository.save(regular);
            archiveUserRepository.save(moderator);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }
}
