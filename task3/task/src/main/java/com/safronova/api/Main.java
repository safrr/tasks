package com.safronova.api;

import com.safronova.api.fetcher.DataFetcher;
import com.safronova.api.fetcher.impl.DataFetcherImpl;
import com.safronova.api.preserver.DataPreserver;
import com.safronova.api.preserver.impl.DataPreserverDBImpl;
import com.safronova.api.preserver.impl.DataPreserverFileImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static String getParameter(String[] args, String parameterName) {
        String temp = Arrays.stream(args).filter(arg -> arg.startsWith(parameterName)).findFirst().orElse(null);
        if (temp == null) {
            return null;
        }
        String[] parts = temp.split("=");
        return parts[1];
    }

    public static DataFetcher createFetcher(String[] args) throws Exception {
        if (args.length >= 2) {
            String path = getParameter(args, "-Dcoordinates");
            String date = getParameter(args, "-Ddate");
            if (path == null) {
                throw new Exception("Path for input file not provided");
            }
            if (date == null) {
                throw new Exception("Date not provided");
            }
            return new DataFetcherImpl(path, date);
        }
        throw new Exception("Date and path for input file not provided");
    }

    public static DataPreserver createPreserver(String[] args) throws Exception {
        if (args.length > 0) {
            String storage = getParameter(args, "-Dstorage");
            if (storage != null) {
                switch (storage) {
                    case ("db"):
                        return new DataPreserverDBImpl();
                    case ("file"):
                        String outPath = getParameter(args, "-Doutpath");
                        if (outPath == null) {
                            throw new Exception("Path for output file is absent");
                        }
                        return new DataPreserverFileImpl(outPath);
                }
            }
        }
        throw new Exception("Storage type not provided");
    }

    public static void main(String[] args) {
        try {
            DataFetcher fetcher = createFetcher(args);
            DataPreserver preserver = createPreserver(args);
            preserver.preserve(fetcher.fetch());
        } catch (IOException e) {
            logger.error("Can not get data from api");
        } catch (Exception e) {
            logger.error("Can not parse parameters");
        }
    }
}
