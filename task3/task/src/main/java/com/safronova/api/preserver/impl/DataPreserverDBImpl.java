package com.safronova.api.preserver.impl;

import com.safronova.api.entity.Crime;
import com.safronova.api.pool.DataSource;
import com.safronova.api.preserver.DataPreserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class DataPreserverDBImpl implements DataPreserver {
    private static final Logger logger = LoggerFactory.getLogger(DataPreserverDBImpl.class);

    private static final String INSERT_STREET_SQL =
            "INSERT INTO streets (s_id, s_name) VALUES(?,?)";

    private static final String INSERT_LOCATION_SQL =
            "INSERT INTO locations  (l_latitude, l_street, l_longitude) VALUES(?,?,?)";

    private static final String INSERT_OUTCOME_SQL = "INSERT INTO outcome_statuses " +
            "(os_date, os_category) VALUES(?,?)";

    private static final String INSERT_CRIME_SQL =
            "INSERT INTO crimes (c_id, c_category, c_location_type, " +
                    "c_location, c_context, c_outcome_status, c_persistent_id, " +
                    "c_location_subtype, c_month) VALUES(?,?,?,?,?,?,?,?,?)";

    @Override
    public void preserve(Object[] input) {
        List<Crime> crimes = Arrays.stream(input).map(o -> (Crime) o).collect(Collectors.toList());
        try {
            insertData(crimes);
        } catch (Exception e) {
            logger.error("Insert data to db failed", e);
        }
    }

    public void insertData(List<Crime> crimes) throws Exception {
        String firstDay = "-01";
        HashSet<Integer> streetSet = new HashSet<>();
        try (Connection connection = DataSource.getConnection()) {
            for (Crime crime : crimes) {
                try (PreparedStatement streetStatement = connection.prepareStatement(INSERT_STREET_SQL);
                     PreparedStatement locationStatement = connection.prepareStatement(INSERT_LOCATION_SQL, Statement.RETURN_GENERATED_KEYS);
                     PreparedStatement outcomeStatement = connection.prepareStatement(INSERT_OUTCOME_SQL, Statement.RETURN_GENERATED_KEYS);
                     PreparedStatement crimeStatemenet = connection.prepareStatement(INSERT_CRIME_SQL)) {

                    if (!streetSet.contains(crime.getLocation().getStreet().getid())) {
                        streetSet.add(crime.getLocation().getStreet().getid());
                        streetStatement.setInt(1, crime.getLocation().getStreet().getid());
                        streetStatement.setString(2, crime.getLocation().getStreet().getname());
                        streetStatement.execute();

                        locationStatement.setFloat(1, Float.parseFloat(crime.getLocation().getLatitude()));
                        locationStatement.setInt(2, crime.getLocation().getStreet().getid());
                        locationStatement.setFloat(3, Float.parseFloat(crime.getLocation().getLongitude()));
                        locationStatement.execute();

                        try (ResultSet generatedKeys = locationStatement.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                crimeStatemenet.setInt(4, generatedKeys.getInt(1));
                            } else {
                                throw new SQLException("Creating location key failed, no ID obtained.");
                            }
                        }

                        if (crime.getOutcomeStatus() != null) {
                            outcomeStatement.setDate(1, Date.valueOf(crime.getOutcomeStatus().getDate() + firstDay));
                            outcomeStatement.setString(2, crime.getOutcomeStatus().getCategory());
                            outcomeStatement.execute();
                            try (ResultSet generatedKeys = outcomeStatement.getGeneratedKeys()) {
                                if (generatedKeys.next()) {
                                    crimeStatemenet.setInt(6, generatedKeys.getInt(1));
                                } else {
                                    throw new SQLException("Creating outcomeStatus key failed, no ID obtained.");
                                }
                            }
                        } else {
                            crimeStatemenet.setNull(6, Types.INTEGER);
                        }

                        crimeStatemenet.setInt(1, crime.getId());
                        crimeStatemenet.setString(2, crime.getCategory());
                        crimeStatemenet.setString(3, crime.getLocationType());
                        crimeStatemenet.setString(5, crime.getContext());
                        crimeStatemenet.setString(7, crime.getPersistentId());
                        crimeStatemenet.setString(8, crime.getLocationSubtype());
                        crimeStatemenet.setDate(9, Date.valueOf(crime.getMonth() + "-01"));
                        crimeStatemenet.execute();
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Error inserting data in db");
            throw new Exception("Can't handle request");
        }
    }
}
