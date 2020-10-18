package com.Services;

import com.Entities.Location;

import java.util.List;

public interface LocationService {
    public List<Location> getAllLocations();
    public void deleteLocation(Integer id);
    public Location createLocation(String buildingName, Integer roomNumber);
}
