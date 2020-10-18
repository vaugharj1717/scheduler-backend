package com.Controllers;

import com.DAOs.LocationDAO;
import com.Entities.Location;
import com.Services.LocationService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/location")
public class LocationController {
    @Autowired
    LocationService locationService;

    @RequestMapping(method= RequestMethod.GET)
    @PreAuthorize("hasAuthority('SCHEDULER')")
    public ResponseEntity<List<Location>> getAllLocations() {
        try {
            List<Location> locationList = locationService.getAllLocations();
            return new ResponseEntity<List<Location>>(locationList, HttpStatus.OK);
        } catch (Exception e) {
            return  new ResponseEntity<List<Location>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/{locationId}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('SCHEDULER')")
    public ResponseEntity<Location> deleteLocation(@PathVariable Integer locationId) {
        try {
            locationService.deleteLocation(locationId);
            return new ResponseEntity<Location>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Location>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('SCHEDULER')")
    public ResponseEntity<Location> createLocation( @RequestBody JsonNode body) {
        try {
            String buildingName = body.get("buildingName").asText();
            Integer roomNumber = body.get("roomNumber").asInt();
            locationService.createLocation(buildingName, roomNumber);
            return new ResponseEntity<Location>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Location>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
