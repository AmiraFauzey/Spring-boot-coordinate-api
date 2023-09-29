package com.mira.webServices.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@Table(name="postcodelatlng")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostcodeLatitudeLongitude implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long  postcodeLocationId;

    @Column(name = "postcode")
    private String postcode;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;
}
