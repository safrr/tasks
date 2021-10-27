package com.safronova.api.preserver.impl;

import com.safronova.api.entity.Crime;
import com.safronova.api.entity.Location;
import com.safronova.api.entity.OutcomeStatus;
import com.safronova.api.entity.Street;
import com.safronova.api.preserver.DBPreserver;
import com.safronova.api.preserver.table.SQLCrime;
import com.safronova.api.preserver.table.SQLLocation;
import com.safronova.api.preserver.table.SQLOutcomeStatus;
import com.safronova.api.preserver.table.SQLStreet;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CrimePreserverDBImpl implements DBPreserver {
    private static final SQLStreet SQL_STREET = new SQLStreet();
    private static final SQLLocation SQL_LOCATION = new SQLLocation();
    private static final SQLOutcomeStatus SQL_OUTCOME_STATUS = new SQLOutcomeStatus();
    private static final SQLCrime SQL_CRIME = new SQLCrime();

    public <T> void saveData(List<T> objects) {
        List<Crime> crimes = objects.stream().map(o -> (Crime) o).collect(Collectors.toList());
        List<Street> streets = new LinkedList<>();
        List<Location> locations = new LinkedList<>();
        List<OutcomeStatus> outcomeStatuses = new LinkedList<>();

        for (Crime crime : crimes) {
            if (crime.getOutcomeStatus() != null) {
                outcomeStatuses.add(crime.getOutcomeStatus());
            }
            if (crime.getLocation() != null) {
                locations.add(crime.getLocation());
                streets.add(crime.getLocation().getStreet());
            }
        }

        SQL_STREET.saveStreets(streets);
        SQL_LOCATION.saveLocations(locations);
        SQL_OUTCOME_STATUS.saveOutcomeStatuses(outcomeStatuses);
        SQL_CRIME.saveCrimes(crimes);
    }
}
