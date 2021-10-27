package com.safronova.api.preserver.table;

import com.safronova.api.entity.OutcomeStatus;
import com.safronova.api.pool.ConnectionPoolHolder;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcBuilder;
import org.codejargon.fluentjdbc.api.query.Query;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class SQLOutcomeStatus {
    private static final String ID_FIELD = "os_id";

    private static final String ADD_OUTCOME_STATUS = "INSERT INTO outcome_statuses (os_date, os_category) VALUES(?,?)";
    private static final String GET_OUTCOME_BY_CATEGORY_AND_DATE = "SELECT os_id FROM outcome_statuses WHERE os_date = ? AND os_category = ?;";

    DataSource dataSource = ConnectionPoolHolder.getInstance().getConnectionPool().getSource();
    FluentJdbc fluentJdbc = new FluentJdbcBuilder()
            .connectionProvider(dataSource)
            .build();
    Query query = fluentJdbc.query();

    public long getOutcomeStatusId(OutcomeStatus outcomeStatus) {
        if (outcomeStatus == null) {
            return -1L;
        }
        Optional<Long> id = query.select(GET_OUTCOME_BY_CATEGORY_AND_DATE)
                .params(outcomeStatus.getDate(),
                        outcomeStatus.getCategory()
                ).firstResult((resultSet ->
                        (resultSet.getLong(ID_FIELD))));
        return id.orElse(-1L);
    }

    private boolean outcomeStatusExists(OutcomeStatus outcomeStatus) {
        return getOutcomeStatusId(outcomeStatus) != -1;
    }

    private void addOutcomeStatus(OutcomeStatus outcomeStatus) {
        query.update(ADD_OUTCOME_STATUS)
                .params(outcomeStatus.getDate(),
                        outcomeStatus.getCategory())
                .run();
    }

    public void saveOutcomeStatuses(List<OutcomeStatus> outcomeStatuses) {
        for (OutcomeStatus outcomeStatus : outcomeStatuses) {
            if (outcomeStatus != null && !outcomeStatusExists(outcomeStatus)) {
                addOutcomeStatus(outcomeStatus);
            }
        }

    }
}
