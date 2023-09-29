package com.mira.webServices.service;

import com.mira.webServices.exceptionHandler.ResourceNotFoundException;
import com.mira.webServices.model.DistanceResponse;
import com.mira.webServices.model.Location;
import com.mira.webServices.model.PostcodeLatitudeLongitude;
import com.mira.webServices.repository.PostcodeLatitudeLongitudeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Optional;

@Service
public class PostcodeLatitudeLongitudeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostcodeLatitudeLongitudeService.class);
    private final PostcodeLatitudeLongitudeRepository postcodeLatitudeLongitudeRepository;
    private final static double EARTH_RADIUS = 6371; // radius in kilometers

    public PostcodeLatitudeLongitudeService(PostcodeLatitudeLongitudeRepository postcodeLatitudeLongitudeRepository) {
        this.postcodeLatitudeLongitudeRepository = postcodeLatitudeLongitudeRepository;
    }

    //1.calculate distance between two points
    public DistanceResponse calculateDistance(String postalCode1, String postalCode2){
        // Fetch latitude and longitude for postalCode1 and postalCode2 from the database
        PostcodeLatitudeLongitude location1 = postcodeLatitudeLongitudeRepository.findByPostcode(postalCode1);
        PostcodeLatitudeLongitude location2 = postcodeLatitudeLongitudeRepository.findByPostcode(postalCode2);

        if (location1 == null || location2 == null) {
            throw new ResourceNotFoundException("One or both postal codes not found.");
        }

        // Log the request details
        LOGGER.info("Received request to calculate distance between postal codes: {} and {}", postalCode1, postalCode2);

        double distance = calculateHaversineDistance(
                Double.parseDouble(location1.getLatitude()),
                Double.parseDouble(location1.getLongitude()),
                Double.parseDouble(location2.getLatitude()),
                Double.parseDouble(location2.getLongitude())
        );

        // Round the distance to two decimal places
        double roundedDistance = Math.round(distance * 100.0) / 100.0;

        DistanceResponse response = new DistanceResponse();
        response.setLocation1(mapLocation(location1));
        response.setLocation2(mapLocation(location2));
        response.setDistance(roundedDistance);
        response.setUnit("km");

        // Log the result of the distance calculation
        LOGGER.info("Distance calculated: {} km", response.getDistance());

        return response;
    }

    private Location mapLocation(PostcodeLatitudeLongitude location) {
        Location mappedLocation = new Location();
        mappedLocation.setPostalCode(location.getPostcode());
        mappedLocation.setLatitude(Double.parseDouble(location.getLatitude()));
        mappedLocation.setLongitude(Double.parseDouble(location.getLongitude()));
        return mappedLocation;
    }

    private double calculateHaversineDistance(double latitude1, double longitude1, double latitude2, double longitude2) {

        // Using Haversine formula! See Wikipedia;
        double lon1Radians = Math.toRadians(longitude1);
        double lon2Radians = Math.toRadians(longitude2);
        double lat1Radians = Math.toRadians(latitude1);
        double lat2Radians = Math.toRadians(latitude2);

        double a = haversine(lat1Radians, lat2Radians)
                + Math.cos(lat1Radians) * Math.cos(lat2Radians) * haversine(lon1Radians, lon2Radians);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (EARTH_RADIUS * c);
    }

    private double haversine(double deg1, double deg2) {
        return square(Math.sin((deg1 - deg2) / 2.0));
    }
    private double square(double x) {
        return x * x;
    }

    //2. update location coordinate
    public PostcodeLatitudeLongitude updateCoordinate(Long postcodeLocationId,PostcodeLatitudeLongitude postcodeLatitudeLongitude){

        //Retrieve the existing Postcode by its ID
        PostcodeLatitudeLongitude location = postcodeLatitudeLongitudeRepository.findById(postcodeLocationId).orElseThrow(
                () -> new ResourceNotFoundException("Postcode not found with id: " + postcodeLocationId));

        // Update the properties of the existing postcode with the new values
        location.setPostcode(postcodeLatitudeLongitude.getPostcode());
        location.setLatitude(postcodeLatitudeLongitude.getLatitude());
        location.setLongitude(postcodeLatitudeLongitude.getLongitude());

        // Save the updated coordinate
        return postcodeLatitudeLongitudeRepository.save(location);
    }
}
