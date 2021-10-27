package com.safronova.api.preserver.table;

import com.safronova.api.entity.StopAndSearch;
import com.safronova.api.pool.ConnectionPoolHolder;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcBuilder;
import org.codejargon.fluentjdbc.api.query.Query;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class SQLStopAndSearch {
    private static final SQLLocation SQL_LOCATION = new SQLLocation();
    private static final String ID_FIELD = "ss_id";
    private static final String ADD_STOP_AND_SEARCH = "INSERT INTO stop_and_searches (ss_type," +
            "ss_involved_person, ss_date_time, ss_operation," +
            "ss_operation_name, ss_location_id, ss_gender, ss_age_range, " +
            "ss_self_defined_ethnicity, ss_officer_defined_ethnicity, " +
            "ss_legislation, ss_object_of_search, ss_outcome, ss_outcome_linked_to_object_of_search," +
            "ss_removal_of_more_than_outer_clothing) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

    private static final String GET_STOP_AND_SEARCH_BY_FIELDS = "SELECT ss_id " +
            "FROM stop_and_searches WHERE ss_type = ?" +
            "AND ss_involved_person = ?" +
            "AND ss_date_time = ?" +
            "AND ss_operation = ?" +
            "AND ss_operation_name = ?" +
            "AND ss_location_id = ?" +
            "AND ss_gender = ?" +
            "AND ss_age_range = ?" +
            "AND ss_self_defined_ethnicity = ?" +
            "AND ss_officer_defined_ethnicity = ?" +
            "AND ss_legislation = ?" +
            "AND ss_object_of_search = ?" +
            "AND ss_outcome = ?" +
            "AND ss_outcome_linked_to_object_of_search = ?" +
            "AND ss_removal_of_more_than_outer_clothing = ?";


    DataSource dataSource = ConnectionPoolHolder.getInstance().getConnectionPool().getSource();
    FluentJdbc fluentJdbc = new FluentJdbcBuilder()
            .connectionProvider(dataSource)
            .build();
    Query query = fluentJdbc.query();


    private long getStopAndSearchId(StopAndSearch stopAndSearch) {

        Long locationId = null;
        long testId = SQL_LOCATION.getLocationId(stopAndSearch.getLocation());
        if (testId != -1) {
            locationId = testId;
        }
        Optional<Long> id = query.select(GET_STOP_AND_SEARCH_BY_FIELDS)
                .params(stopAndSearch.getType(),
                        stopAndSearch.isInvolvedPerson(),
                        stopAndSearch.getDateTime(),
                        stopAndSearch.isOperation(),
                        stopAndSearch.getOperationName(),
                        locationId,
                        stopAndSearch.getGender(),
                        stopAndSearch.getAgeRange(),
                        stopAndSearch.getSelfDefinedEthnicity(),
                        stopAndSearch.getOfficerDefinedEthnicity(),
                        stopAndSearch.getLegislation(),
                        stopAndSearch.getObjectOfSearch(),
                        stopAndSearch.getOutcome(),
                        stopAndSearch.getOutcomeLinkedToObjectOfSearch(),
                        stopAndSearch.isRemovalOfMoreThanOuterClothing())
                .firstResult((resultSet ->
                        (resultSet.getLong(ID_FIELD))));
        return id.orElse(-1L);
    }

    private boolean stopAndSearchExists(StopAndSearch stopAndSearch) {
        return getStopAndSearchId(stopAndSearch) != -1;
    }


    private void addStopAndSearch(StopAndSearch stopAndSearch) {
        Long locationId = null;
        long id = SQL_LOCATION.getLocationId(stopAndSearch.getLocation());
        if (id != -1) {
            locationId = id;
        }
        query.update(ADD_STOP_AND_SEARCH)
                .params(stopAndSearch.getType(),
                        stopAndSearch.isInvolvedPerson(),
                        stopAndSearch.getDateTime(),
                        stopAndSearch.isOperation(),
                        stopAndSearch.getOperationName(),
                        locationId,
                        stopAndSearch.getGender(),
                        stopAndSearch.getAgeRange(),
                        stopAndSearch.getSelfDefinedEthnicity(),
                        stopAndSearch.getOfficerDefinedEthnicity(),
                        stopAndSearch.getLegislation(),
                        stopAndSearch.getObjectOfSearch(),
                        stopAndSearch.getOutcome(),
                        stopAndSearch.getOutcomeLinkedToObjectOfSearch(),
                        stopAndSearch.isRemovalOfMoreThanOuterClothing())
                .run();
    }

    public void saveStopAndSearches(List<StopAndSearch> stopAndSearches) {
        for (StopAndSearch stopAndSearch : stopAndSearches) {
            if (stopAndSearch != null && !stopAndSearchExists(stopAndSearch)) {
                addStopAndSearch(stopAndSearch);
            }
        }
    }
}
