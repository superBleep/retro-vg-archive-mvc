package com.superbleep.rvgamvc.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Platform {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String manufacturer;

    @Temporal(TemporalType.DATE)
    private Date release;

    @OneToMany(mappedBy = "platform", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Game> games;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "emulator_platform",
        joinColumns = @JoinColumn(name = "emulator_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "platform_id", referencedColumnName = "id"))
    private List<Emulator> emulators;
}
