package com.superbleep.rvgamvc.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Emulator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String developer;

    @Temporal(TemporalType.DATE)
    private Date release;

    @ManyToMany(mappedBy = "emulators", cascade = CascadeType.ALL)
    private List<Platform> platforms;
}
