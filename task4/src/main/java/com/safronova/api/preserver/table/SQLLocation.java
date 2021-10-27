package com.safronova.api.preserver.table;

import com.safronova.api.entity.Location;
import com.safronova.api.pool.ConnectionPoolHolder;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcBuilder;
import org.codejargon.fluentjdbc.api.query.Query;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class SQLLocation {
    private static final String ID_FIELD = "l_id";
    private static final String ADD_LOCATION = "INSERT INTO locations (l_latitude, l_street, l_longitude) VALUES(?,?,?)";
    private static final String GET_LOCATION_BY_LAT_AND_LONG = "SELECT l_id FROM locations WHERE l_latitude = ? AND l_longitude = ?;";

    DataSource dataSource = ConnectionPoolHolder.getInstance().getConnectionPool().getSource();
    FluentJdbc fluentJdbc = new FluentJdbcBuilder()
            .connectionProvider(dataSource)
            .build();
    Query query = fluentJdbc.query();

    public long getLocationId(Location location) {
        if (location == null) {
            return -1L;
        }
        Optional<Long> id = query.select(GET_LOCATION_BY_LAT_AND_LONG)
                .params(location.getLatitude(),
                        location.getLongitude()).firstResult((resultSet ->
                        (resultSet.getLong(ID_FIELD))));
        return id.orElse(-1L);
    }

    private boolean locationExists(Location location) {
        return getLocationId(location) != -1;
    }

    public void saveLocations(List<Location> locations) {
        for (Location location : locations) {
            if (location != null && !locationExists(location)) {
                addLocation(location);
            }
        }
    }

    private void addLocation(Location location) {
        query.update(ADD_LOCATION)
                .params(location.getLatitude(),
                        location.getStreet().getId(),
                        location.getLongitude())
                .run();
    }
}

