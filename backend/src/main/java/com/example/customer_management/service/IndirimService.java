package com.example.customer_management.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class IndirimService {
    private static final String API_URL = "https://api.random.org/json-rpc/4/invoke";
    private static final String API_KEY = "3c157d58-6056-43c2-8d26-6b3f6d1bd5e4";

    public BigDecimal getIndirimOrani() {
        RestTemplate restTemplate = new RestTemplate();

        String body = """
        {
          "jsonrpc": "2.0",
          "method": "generateIntegers",
          "params": {
            "apiKey": "%s",
            "n": 1,
            "min": 0,
            "max": 30
          },
          "id": 42
        }
        """.formatted(API_KEY);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, Map.class);

        Map<String, Object> responseBody = response.getBody();
        if (responseBody == null || !responseBody.containsKey("result")) {
            throw new RuntimeException("Random.org API returned no result.");
        }

        Map<String, Object> result = (Map<String, Object>) responseBody.get("result");
        Map<String, Object> random = (Map<String, Object>) result.get("random");
        java.util.List<Integer> data = (java.util.List<Integer>) random.get("data");

        if (data == null || data.isEmpty()) {
            throw new RuntimeException("Random.org API returned empty data.");
        }

        Integer indirimYuzdesi = data.get(0);
        return BigDecimal.valueOf(indirimYuzdesi).divide(BigDecimal.valueOf(100));
    }
}

