package com.mira.webServices.controller;

import com.mira.webServices.model.DistanceResponse;
import com.mira.webServices.model.PostcodeLatitudeLongitude;
import com.mira.webServices.service.PostcodeLatitudeLongitudeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("PostcodeDistance")
public class PostcodeLatitudeLongitudeController {

    private final PostcodeLatitudeLongitudeService postcodeLatitudeLongitudeService;

    public PostcodeLatitudeLongitudeController(PostcodeLatitudeLongitudeService postcodeLatitudeLongitudeService) {
        this.postcodeLatitudeLongitudeService = postcodeLatitudeLongitudeService;
    }

    //1. calculate distance between two location
    @GetMapping("/calculateDistance")
    public DistanceResponse calculateDistance(
            @RequestParam(name = "postalCode1") String postalCode1,
            @RequestParam(name = "postalCode2") String postalCode2) {

        DistanceResponse response = postcodeLatitudeLongitudeService.calculateDistance(postalCode1, postalCode2);

        return response;
    }

    //3. Update the Bookmark
    @PutMapping("{postcodeLocationId}")
    public PostcodeLatitudeLongitude updateCoordinate(
            @PathVariable Long postcodeLocationId,
            @RequestBody PostcodeLatitudeLongitude updatedCoordinate){

        PostcodeLatitudeLongitude updated = postcodeLatitudeLongitudeService.updateCoordinate(postcodeLocationId, updatedCoordinate);
        return updated;
    }
}
