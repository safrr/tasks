package com.safronova.api.fetcher.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.safronova.api.entity.Crime;
import com.safronova.api.fetcher.DataFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


public class DataFetcherImpl implements DataFetcher {
    private static final Logger logger = LoggerFactory.getLogger(DataFetcherImpl.class);
    private String path;
    private final String date;
    private final Coordinates[] coordinates;

    public DataFetcherImpl(String path, String date) throws IOException {
        this.path = path;
        this.date = date;
        this.coordinates = parseCoordinates(path);
    }

    @Override
    public Object[] fetch() {
        String temp = "https://data.police.uk/api/crimes-street/all-crime?date=" + date;
        ArrayList<Crime> crimes = new ArrayList<>();
        for (Coordinates coordinate : coordinates) {
            URL url = null;
            try {
                url = new URL(temp + "&lat=" + coordinate.getLat() + "&lng=" + coordinate.getLng());
            } catch (MalformedURLException e) {
                logger.error("Invalid url");
            }
            if (url != null) {
                try (InputStreamReader reader = new InputStreamReader(url.openStream())) {
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    crimes.addAll(Arrays.asList(gson.fromJson(reader, Crime[].class)));
                } catch (IOException e) {
                    logger.error("Api endpoint for position is not available. Skipping...");
                }
            }
        }
        return crimes.toArray();
    }

    public Coordinates[] parseCoordinates(String path) throws IOException {
        String content = Files.lines(Paths.get(path)).collect(Collectors.joining("\n"));
        String[] lines = content.split("\n+");
        Coordinates[] coordinates = new Coordinates[lines.length - 1]; // skip first
        for (int i = 1; i < lines.length; i++) {
            String[] parts = lines[i].split(",");
            coordinates[i - 1] = new Coordinates(parts[0], Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
        }
        return coordinates;
    }
}

class Coordinates {
    private String name;
    private double lat;
    private double lng;

    public Coordinates(String name, double lng, double lat) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
