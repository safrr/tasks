package com.safronova.api.fetcher.impl;

import com.safronova.api.entity.Crime;
import com.safronova.api.entity.Force;
import com.safronova.api.entity.Point;
import com.safronova.api.entity.StopAndSearch;
import com.safronova.api.fetcher.PointsParser;
import com.safronova.api.fetcher.Request;
import com.safronova.api.fetcher.URLHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class FetchCrimeImpl {
    private static final Logger logger = LoggerFactory.getLogger(FetchCrimeImpl.class);
    private static final PointsParser COORDINATES_HANDLER = new PointsParser();
    private static final String FORCES_URL = "https://data.police.uk/api/forces";

    private List<Point> getPointsFromFile(String path) {
        List<Point> points = COORDINATES_HANDLER.parsePoints(path);
        if (points == null || points.isEmpty()) {
            logger.error("No coordinates in file");
            throw new RuntimeException();
        }
        return points;
    }

    public List<Crime> fetchCrime(LocalDate date, String pathToPoints) {
        List<Point> points = getPointsFromFile(pathToPoints);
        List<Crime> crimes = new LinkedList<>();
        for (Point point : points) {
            crimes.addAll(Request.doRequest(URLHandler.buildCrimesURL(date, point), Crime.class));
        }
        return crimes;
    }

    public List<StopAndSearch> fetchStop(LocalDate date) {
        List<Force> forces = Request.doRequest(FORCES_URL, Force.class);
        List<StopAndSearch> stopAndSearches = new LinkedList<>();
        for (Force force : forces){
            stopAndSearches.addAll(Request.doRequest(URLHandler.buildStopsAndSearchesURL(date, force), StopAndSearch.class));
        }
        return stopAndSearches;
    }
}
