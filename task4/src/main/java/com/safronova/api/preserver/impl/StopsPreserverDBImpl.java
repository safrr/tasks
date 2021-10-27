package com.safronova.api.preserver.impl;

import com.safronova.api.entity.Location;
import com.safronova.api.entity.StopAndSearch;
import com.safronova.api.entity.Street;
import com.safronova.api.preserver.DBPreserver;
import com.safronova.api.preserver.table.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class StopsPreserverDBImpl implements DBPreserver {
    private static final SQLStreet SQL_STREET = new SQLStreet();
    private static final SQLLocation SQL_LOCATION = new SQLLocation();
    private static final SQLStopAndSearch SQL_STOP_AND_SEARCH = new SQLStopAndSearch();

    public <T> void saveData(List<T> objects) {
        List<StopAndSearch> stopAndSearches = objects.stream().map(s -> (StopAndSearch) s).collect(Collectors.toList());
        List<Street> streets = new LinkedList<>();
        List<Location> locations = new LinkedList<>();

        for (StopAndSearch stopAndSearch : stopAndSearches) {
            if (stopAndSearch.getLocation() != null) {
                locations.add(stopAndSearch.getLocation());
                streets.add(stopAndSearch.getLocation().getStreet());
            }
        }

        SQL_STREET.saveStreets(streets);
        SQL_LOCATION.saveLocations(locations);
        SQL_STOP_AND_SEARCH.saveStopAndSearches(stopAndSearches);
    }
}
