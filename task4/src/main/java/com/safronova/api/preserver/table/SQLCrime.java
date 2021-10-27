package com.safronova.api.preserver.table;

import com.safronova.api.entity.Crime;
import com.safronova.api.pool.ConnectionPoolHolder;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcBuilder;
import org.codejargon.fluentjdbc.api.query.Query;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class SQLCrime {
    private static final String ID_FIELD = "c_id";
    private static final SQLLocation SQL_LOCATION = new SQLLocation();
    private static final SQLOutcomeStatus SQL_OUTCOME_STATUS = new SQLOutcomeStatus();

    private static final String ADD_CRIME_WITH_OUTCOME = "INSERT INTO crimes (c_id, c_category, c_location_type, c_location, c_context, c_outcome_status, c_persistent_id, c_location_subtype, c_month) VALUES(?,?,?,?,?,?,?,?,?)";

    private static final String GET_CRIME_BY_ID = "SELECT c_id FROM crimes WHERE c_id = ?;";

    DataSource dataSource = ConnectionPoolHolder.getInstance().getConnectionPool().getSource();
    FluentJdbc fluentJdbc = new FluentJdbcBuilder()
            .connectionProvider(dataSource)
            .build();
    Query query = fluentJdbc.query();

    private long getCrimeId(Crime crime) {
        Optional<Long> id = query.select(GET_CRIME_BY_ID)
                .params(crime.getId())
                .firstResult((resultSet ->
                        (resultSet.getLong(ID_FIELD))));
        return id.orElse(-1L);
    }

    private boolean crimeExists(Crime crime) {
        return getCrimeId(crime) != -1;
    }

    private void addCrime(Crime crime) {
        Long outcomeStatusId = null;
        long id = SQL_OUTCOME_STATUS.getOutcomeStatusId(crime.getOutcomeStatus());
        if (id != -1) {
            outcomeStatusId = id;
        }

        query.update(ADD_CRIME_WITH_OUTCOME)
                .params(crime.getId(),
                        crime.getCategory(),
                        crime.getLocationType(),
                        SQL_LOCATION.getLocationId(crime.getLocation()),
                        crime.getContext(),
                        outcomeStatusId,
                        crime.getPersistentId(),
                        crime.getLocationSubtype(),
                        crime.getMonth())
                .run();
    }

    public void saveCrimes(List<Crime> crimes) {
        for (Crime crime : crimes) {
            if (crime != null && !crimeExists(crime)) {
                addCrime(crime);
            }
        }
    }
}

