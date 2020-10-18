package com.Services;

import com.DAOs.LocationDAO;
import com.Entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements  LocationService {

    @Autowired
    LocationDAO locationDAO;
    @Override
    public List<Location> getAllLocations() {
        return locationDAO.getAll();
    }

    @Override
    public void deleteLocation(Integer id) {
        locationDAO.remove(id);
    }
}
