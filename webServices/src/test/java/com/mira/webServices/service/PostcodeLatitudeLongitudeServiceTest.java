package com.mira.webServices.service;

import com.mira.webServices.model.DistanceResponse;
import com.mira.webServices.model.PostcodeLatitudeLongitude;
import com.mira.webServices.repository.PostcodeLatitudeLongitudeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PostcodeLatitudeLongitudeServiceTest {

    @Test
    void calculateDistance() {


        //We use repository because simulation with database
        // Create a mock repository
        PostcodeLatitudeLongitudeRepository repository = Mockito.mock(PostcodeLatitudeLongitudeRepository.class);

        //Because we want to simulate  and check that function does what is supposed to do
        // Create a test instance of the service with the mock repository
        PostcodeLatitudeLongitudeService service = new PostcodeLatitudeLongitudeService(repository);

        // Create sample data for location1 and location2
        PostcodeLatitudeLongitude location1 = new PostcodeLatitudeLongitude();
        location1.setPostcode("AB10 1XG");
        location1.setLatitude(57.1441560);
        location1.setLongitude(-2.1148640);

        PostcodeLatitudeLongitude location2 = new PostcodeLatitudeLongitude();
        location2.setPostcode("AB10 6RM");
        location2.setLatitude(57.1378720);
        location2.setLongitude(-2.1214880);

        // Mock the behavior of the repository to return the sample data when findByPostcode is called
        // We simulate behaviour of database
        Mockito.when(repository.findByPostcode("AB10 1XG")).thenReturn(location1);
        Mockito.when(repository.findByPostcode("AB10 6RM")).thenReturn(location2);

        // Calculate the distance
        DistanceResponse response = service.calculateDistance("AB10 1XG", "AB10 6RM");

        // Perform assertions on the response
        //
        assertNotNull(response);
        assertEquals("AB10 1XG", response.getLocation1().getPostcode());
        assertEquals(57.1441560, response.getLocation1().getLatitude());
        assertEquals(-2.1148640, response.getLocation1().getLongitude());
        assertEquals("AB10 6RM", response.getLocation2().getPostcode());
        assertEquals(57.1378720, response.getLocation2().getLatitude());
        assertEquals(-2.1214880, response.getLocation2().getLongitude());
        assertEquals(0.8, response.getDistance(), 0.01); // Use delta for double comparisons
        assertEquals("km", response.getUnit());
    }

    @Test
    void updateCoordinate() {
        // Create a mock repository
        PostcodeLatitudeLongitudeRepository repository = Mockito.mock(PostcodeLatitudeLongitudeRepository.class);

        // Create a test instance of the service with the mock repository
        PostcodeLatitudeLongitudeService service = new PostcodeLatitudeLongitudeService(repository);

        // Create sample data for the existing location
        PostcodeLatitudeLongitude existingLocation = new PostcodeLatitudeLongitude();
        existingLocation.setPostcode("AB10 6RM");
        existingLocation.setLatitude(57.1378720);
        existingLocation.setLongitude(-2.1214880);

        // Create a sample updated location
        PostcodeLatitudeLongitude updatedLocation = new PostcodeLatitudeLongitude();
        updatedLocation.setPostcode("AB10 1XM");
        updatedLocation.setLatitude(57.1378720);
        updatedLocation.setLongitude(-2.1214880);

        // Mock the behavior of the repository to return the existing location when findById is called
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(existingLocation));

        // Mock the behavior of the repository to return the updated location when save is called
        Mockito.when(repository.save(updatedLocation)).thenReturn(updatedLocation);

        // Update the coordinate
        PostcodeLatitudeLongitude result = service.updateCoordinate(1L, updatedLocation);

        // Perform assertions on the updated result
        assertNotNull(result);
        assertEquals("AB10 1XM", result.getPostcode());
        assertEquals(57.1378720, result.getLatitude());
        assertEquals(-2.1214880, result.getLongitude());
    }
}