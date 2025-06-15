package com.superbleep.rvgamvc.domain.security;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Entity
public class ArchiveUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "userId", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authorityId", referencedColumnName = "id")})
    private Set<Authority> authorities;

    ArchiveUser(Long id, String username, String password, Set<Authority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public ArchiveUser() {
    }

    private static Boolean $default$accountNonExpired() {
        return true;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public Set<Authority> getAuthorities() {
        return this.authorities;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public static class UserBuilder {
        private Long id;
        private String username;
        private String password;
        private ArrayList<Authority> authorities;

        UserBuilder() {
        }

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder authority(Authority authority) {
            if (this.authorities == null) this.authorities = new ArrayList<Authority>();
            this.authorities.add(authority);
            return this;
        }

        public UserBuilder authorities(Collection<? extends Authority> authorities) {
            if (authorities == null) {
                throw new NullPointerException("authorities cannot be null");
            }
            if (this.authorities == null) this.authorities = new ArrayList<Authority>();
            this.authorities.addAll(authorities);
            return this;
        }

        public UserBuilder clearAuthorities() {
            if (this.authorities != null)
                this.authorities.clear();
            return this;
        }

        public ArchiveUser build() {
            Set<Authority> authorities;
            switch (this.authorities == null ? 0 : this.authorities.size()) {
                case 0:
                    authorities = java.util.Collections.emptySet();
                    break;
                case 1:
                    authorities = java.util.Collections.singleton(this.authorities.get(0));
                    break;
                default:
                    authorities = new java.util.LinkedHashSet<Authority>(this.authorities.size() < 1073741824 ? 1 +
                            this.authorities.size() + (this.authorities.size() - 3) / 3 : Integer.MAX_VALUE);
                    authorities.addAll(this.authorities);
                    authorities = java.util.Collections.unmodifiableSet(authorities);
            }

            return new ArchiveUser(this.id, this.username, this.password, authorities);
        }

        public String toString() {
            return "ArchiveUser.UserBuilder(id=" + this.id + ", username=" + this.username + ", password=" + this.password +
                    ", authorities=" + this.authorities + ")";
        }
    }
}
