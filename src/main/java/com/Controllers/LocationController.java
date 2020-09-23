package com.Controllers;

import com.DAOs.LocationDAO;
import com.Entities.Location;
import com.Services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }
}
