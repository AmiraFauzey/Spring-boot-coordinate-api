package com.mira.webServices.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@Table(name="POSTCODELOCATION")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostcodeLatitudeLongitude implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POSTCODELOCATION_ID")
    private Long  postcodeLocationId;

    @Column(name = "POSTCODE")
    private String postcode;

    @Column(name = "LATITUDE")
    private String latitude;

    @Column(name = "LONGITUDE")
    private String longitude;
}
