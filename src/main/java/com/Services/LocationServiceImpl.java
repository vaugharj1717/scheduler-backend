package com.Services;

import com.DAOs.LocationDAO;
import com.Entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void deleteLocation(Integer id) {
        locationDAO.remove(id);
    }

    @Override
    @Transactional
    public Location createLocation(String buildingName, Integer roomNumber) {
        Location location = new Location();
        location.setBuildingName(buildingName);
        location.setRoomNumber(roomNumber);
        return locationDAO.saveOrUpdate(location);
    }
}
