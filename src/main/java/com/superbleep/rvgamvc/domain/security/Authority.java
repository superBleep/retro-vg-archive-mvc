package com.superbleep.rvgamvc.domain.security;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;

    @ManyToMany(mappedBy = "authorities")
    private Set<ArchiveUser> archiveUsers;

    Authority(Long id, String role, Set<ArchiveUser> archiveUsers) {
        this.id = id;
        this.role = role;
        this.archiveUsers = archiveUsers;
    }

    public Authority() {
    }

    public static AuthorityBuilder builder() {
        return new AuthorityBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getRole() {
        return this.role;
    }

    public Set<ArchiveUser> getUsers() {
        return this.archiveUsers;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsers(Set<ArchiveUser> archiveUsers) {
        this.archiveUsers = archiveUsers;
    }

    public static class AuthorityBuilder {
        private Long id;
        private String role;
        private Set<ArchiveUser> archiveUsers;

        AuthorityBuilder() {
        }

        public AuthorityBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AuthorityBuilder role(String role) {
            this.role = role;
            return this;
        }

        public AuthorityBuilder users(Set<ArchiveUser> archiveUsers) {
            this.archiveUsers = archiveUsers;
            return this;
        }

        public Authority build() {
            return new Authority(this.id, this.role, this.archiveUsers);
        }

        public String toString() {
            return "Authority.AuthorityBuilder(id=" + this.id + ", role=" + this.role + ", archiveUsers=" + this.archiveUsers + ")";
        }
    }
}
