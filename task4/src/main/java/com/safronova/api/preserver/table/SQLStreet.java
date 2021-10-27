package com.safronova.api.preserver.table;

import com.safronova.api.entity.Street;
import com.safronova.api.pool.ConnectionPoolHolder;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcBuilder;
import org.codejargon.fluentjdbc.api.query.Query;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class SQLStreet {
    private static final String ID_FIELD = "s_id";

    private static final String ADD_STREET = "INSERT INTO streets (s_id, s_name) VALUES(?,?)";

    private static final String GET_STREET_BY_ID = "SELECT s_id FROM streets WHERE s_id = ?;";

    DataSource dataSource = ConnectionPoolHolder.getInstance().getConnectionPool().getSource();
    FluentJdbc fluentJdbc = new FluentJdbcBuilder()
            .connectionProvider(dataSource)
            .build();
    Query query = fluentJdbc.query();

    private long getStreetById(long streetId) {
        Optional<Long> id = query
                .select(GET_STREET_BY_ID)
                .params(streetId).firstResult((resultSet ->
                        (resultSet.getLong(ID_FIELD))));
        return id.orElse(-1L);
    }

    private boolean streetExists(Street street) {
        return getStreetById(street.getId()) != -1;
    }

    public void saveStreets(List<Street> streets) {
        for (Street street : streets) {
            if (street != null && !streetExists(street)) {
                addStreet(street);
            }
        }
    }

    private void addStreet(Street street) {
        query.update(ADD_STREET)
                .params(street.getId(),
                        street.getName())
                .run();
    }
}
