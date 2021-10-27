package com.safronova.api.fetcher;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;


public class Request {
    private static final Logger logger = LoggerFactory.getLogger(Request.class);

    public static <T> List<T> doRequest(String url, Class<T> type) {
        List<T> objects = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpGet request = new HttpGet(url);
            response = httpClient.execute(request);
            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode == 200) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    objects = JSON.parseArray(result, type);
                }
            }
        } catch (IOException e) {
            logger.error(e.toString());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                logger.error(e.toString());
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error(e.toString());
            }
        }

        return objects;
    }
}
