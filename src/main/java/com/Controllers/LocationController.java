package com.Controllers;

import com.DAOs.LocationDAO;
import com.Entities.Location;
import com.Services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {
    @Autowired
    LocationService locationService;

    @CrossOrigin
    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<List<Location>> getAllLocations() {
        try {
            List<Location> locationList = locationService.getAllLocations();
            return new ResponseEntity<List<Location>>(locationList, HttpStatus.OK);
        } catch (Exception e) {
            return  new ResponseEntity<List<Location>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
