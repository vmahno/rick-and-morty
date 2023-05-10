package org.ricknmorty.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class HttpClient {
    private final CloseableHttpClient closeableHttpClient;
    private final ObjectMapper objectMapper;

    public HttpClient(CloseableHttpClient closeableHttpClient,
                      ObjectMapper objectMapper) {
        this.closeableHttpClient = closeableHttpClient;
        this.objectMapper = objectMapper;
    }

    public <T> T get(String url, Class<T> clazz) {
        HttpGet request = new HttpGet(url);
        try (CloseableHttpResponse response = closeableHttpClient.execute(request)) {
            return objectMapper.readValue(response.getEntity().getContent(), clazz);
        } catch (IOException e) {
            throw new RuntimeException("Can't fetch info from URL " + url, e);
        }
    }
}
