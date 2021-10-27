package com.safronova.api.fetcher;

import com.safronova.api.entity.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PointsParser {
    private static final Logger logger = LoggerFactory.getLogger(PointsParser.class);
    private static final String CSV_SPLITTER = ",";
    private static final String CSV_HEADER = "name,longitude,latitude";

    public List<Point> parsePoints(String path) {
        List<Point> points = new ArrayList<>();

        String line;
        logger.debug("File path = {}", path);

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                if (!line.equals(CSV_HEADER)) {
                    String[] point = line.split(CSV_SPLITTER);
                    logger.debug("longitude = {}", point[1]);
                    logger.debug("latitude = {}", point[2]);
                    double longitude = Double.parseDouble(point[1]);
                    double latitude = Double.parseDouble(point[2]);
                    points.add(new Point(longitude, latitude));
                }
            }
        } catch (IOException | NumberFormatException e) {
            logger.error(e.toString());
        }
        return points;
    }
}
