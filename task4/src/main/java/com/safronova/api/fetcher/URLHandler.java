package com.safronova.api.fetcher;

import com.safronova.api.entity.Force;
import com.safronova.api.entity.Point;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class URLHandler {
    private static final String POLICE_API = "https://data.police.uk/api";
    private static final String CRIMES = "/crimes-street/all-crime";
    private static final String STOP_AND_SEARCH = "/stops-force";

    private static final String LATITUDE = "lat";
    private static final String LONGITUDE = "lng";
    private static final String DATE = "date";
    private static final String FORCE = "force";

    public static String formatDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        return date.format(formatter);
    }

    private static String buildURLParameters(Map<String, Object> parameters) {
        Set<String> keys = parameters.keySet();
        if (keys.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (String s : keys) {
            if (isFirst) {
                sb.append("?");
                isFirst = false;
            } else {
                sb.append("&");
            }
            sb.append(s).append("=").append(parameters.get(s));
        }
        return sb.toString();
    }

    public static String buildURL(String uri, Map<String, Object> parameters) {
        return POLICE_API + uri + buildURLParameters(parameters);
    }

    public static String buildCrimesURL(LocalDate date, Point point) {
        String url;
        Map<String, Object> parameters = new LinkedHashMap<>();
        parameters.put(LATITUDE, point.getLatitude());
        parameters.put(LONGITUDE, point.getLongitude());
        parameters.put(DATE, formatDate(date));
        url = buildURL(CRIMES, parameters);
        return url;
    }

    public static String buildStopsAndSearchesURL(LocalDate date, Force force) {
        String url;
        Map<String, Object> parameters = new LinkedHashMap<>();
        parameters.put(FORCE, force.getId());
        parameters.put(DATE, formatDate(date));
        url = buildURL(STOP_AND_SEARCH, parameters);
        return url;
    }
}
