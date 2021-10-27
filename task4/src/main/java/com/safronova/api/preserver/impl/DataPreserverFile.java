package com.safronova.api.preserver.impl;

import com.alibaba.fastjson.JSON;
import com.safronova.api.preserver.FilePreserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DataPreserverFile implements FilePreserver {
    private static final Logger logger = LoggerFactory.getLogger(DataPreserverFile.class);

    public <T> void preserve(String path, List<T> objects) {
        String out = JSON.toJSONString(objects);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.write(out);
        } catch (IOException e) {
            logger.error("Serialization failed." + e.toString());
        }
    }
}
