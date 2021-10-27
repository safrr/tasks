package com.safronova.api;


import com.safronova.api.fetcher.impl.FetchCrimeImpl;
import com.safronova.api.pool.ConnectionPoolHolder;
import com.safronova.api.preserver.DBPreserver;
import com.safronova.api.preserver.FilePreserver;
import com.safronova.api.preserver.impl.CrimePreserverDBImpl;
import com.safronova.api.preserver.impl.DataPreserverFile;
import com.safronova.api.preserver.impl.StopsPreserverDBImpl;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Properties;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final String PROPERTY_DATE = "date";
    private static final String PROPERTY_PATH = "path";
    private static final String PROPERTY_HELP = "help";
    private static final String PROPERTY_SAVE = "save";
    private static final String PROPERTY_OUTPUT = "output";
    private static final String PROPERTY_METHOD = "method";
    private static final String PROPERTY_VALUE_CRIMES = "crimes";
    private static final String PROPERTY_VALUE_STOPS = "stops";
    private static final String PROPERTY_VALUE_SAVE_IN_DB = "db";
    private static final String PROPERTY_VALUE_SAVE_IN_FILE = "file";
    private static final String FIRST_DAY = "-01";

    private static final String HELP_MESSAGE = "This application performs info parsing to database via Street Level Crimes API method and Stop and Search API method  \n" +
            "options:\n" +
            "-Dhelp  Displays help message\n" +
            "next three options required to be input together:\n" +
            "-Dstart=2021-08  Set 2021-08 as start date\n" +
            "-Dend=2021-09  Set 2021-09 as end date\n" +
            "-Dmethod='value'  Set 'value' as 'crimes' for Crimes, or 'stops' for stops and searches\n" +
            "\n" +
            "if you selected method option as 'crimes' you need to add path to file with list of existing coordinates:\n" +
            "-Dpath='value'  Set 'value' as path to file with list of existing coordinates\n" +
            "\n" +
            "-Dsave='value'  Set 'value' 'db' to save in database or 'file' to save in file\n" +
            "if you set save option as 'file' you need to add path to file for saving:\n" +
            "-Doutput='value'   Set 'value' as path to file where to save data\n";

    public static void main(String[] args) {

        Options options = new Options();
        Option propertyOption = Option.builder()
                .longOpt("D")
                .argName("property=value")
                .hasArgs()
                .build();
        options.addOption(propertyOption);

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine line = parser.parse(options, args);
            if (line.hasOption("D")) {
                Properties properties = line.getOptionProperties("D");
                if (properties.getProperty(PROPERTY_HELP) != null) {
                    System.out.println(HELP_MESSAGE);
                    return;
                }

                ConnectionPoolHolder.getInstance().getConnectionPool().init();

                LocalDate date = LocalDate.parse(properties.getProperty(PROPERTY_DATE) + FIRST_DAY);

                if (properties.getProperty(PROPERTY_METHOD).equals(PROPERTY_VALUE_CRIMES)) {
                    FetchCrimeImpl fetcher = new FetchCrimeImpl();
                    String pathToCoordinatesFile = properties.getProperty(PROPERTY_PATH);

                    if (properties.getProperty(PROPERTY_SAVE).equals(PROPERTY_VALUE_SAVE_IN_DB)) {
                        DBPreserver preserver = new CrimePreserverDBImpl();
                        preserver.saveData(fetcher.fetchCrime(date, pathToCoordinatesFile));
                    }

                    if (properties.getProperty(PROPERTY_SAVE).equals(PROPERTY_VALUE_SAVE_IN_FILE)) {
                        String savePath = properties.getProperty(PROPERTY_OUTPUT);
                        FilePreserver preserver = new DataPreserverFile();
                        preserver.preserve(savePath, fetcher.fetchCrime(date, pathToCoordinatesFile));
                    }
                }

                if (properties.getProperty(PROPERTY_METHOD).equals(PROPERTY_VALUE_STOPS)) {
                    FetchCrimeImpl fetcher = new FetchCrimeImpl();
                    if (properties.getProperty(PROPERTY_SAVE).equals(PROPERTY_VALUE_SAVE_IN_DB)) {
                        DBPreserver preserver = new StopsPreserverDBImpl();
                        preserver.saveData(fetcher.fetchStop(date));
                    }

                    if (properties.getProperty(PROPERTY_SAVE).equals(PROPERTY_VALUE_SAVE_IN_FILE)) {
                        String savePath = properties.getProperty(PROPERTY_OUTPUT);
                        FilePreserver preserver = new DataPreserverFile();
                        preserver.preserve(savePath, fetcher.fetchStop(date));
                    }
                }
                ConnectionPoolHolder.getInstance().getConnectionPool().dispose();
            }
        } catch (ParseException e) {
            logger.error("Parsing failed. Reason: " + e.toString());
        }
    }
}
