package com.superbleep.rvgamvc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlatformDTO {
    private String name;
    private String manufacturer;
    private Date release;
}
