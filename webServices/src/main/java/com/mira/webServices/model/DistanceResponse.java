package com.mira.webServices.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistanceResponse {

    private Location location1;
    private Location location2;
    private double distance;
    private String unit;

}
