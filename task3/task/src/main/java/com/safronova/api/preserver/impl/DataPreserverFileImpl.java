package com.safronova.api.preserver.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safronova.api.entity.Crime;
import com.safronova.api.preserver.DataPreserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataPreserverFileImpl implements DataPreserver {
    private static final Logger logger = LoggerFactory.getLogger(DataPreserverFileImpl.class);
    private final String outPath;

    public DataPreserverFileImpl(String outPath) {
        this.outPath = outPath;
    }

    @Override
    public void preserve(Object[] input) {
        List<Crime> crimes = Arrays.stream(input).map(o -> (Crime) o).collect(Collectors.toList());
        try (FileWriter writer = new FileWriter(outPath)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(writer, crimes);
        } catch (IOException e) {
            logger.error("Serialization failed");
        }
    }
}
