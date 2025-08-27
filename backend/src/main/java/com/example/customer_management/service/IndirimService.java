package com.example.customer_management.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class IndirimService {
    private static final String API_URL = "https://api.random.org/json-rpc/4/invoke";
    private static final String API_KEY = "SENIN_API_KEY"; // random.org API key gerekiyor

    public BigDecimal getIndirimOrani() {
        RestTemplate restTemplate = new RestTemplate();

        // JSON-RPC body
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

        // Sonucu al
        Map result = (Map) response.getBody().get("result");
        Map random = (Map) result.get("random");
        Integer indirimYuzdesi = (Integer) ((java.util.List) random.get("data")).get(0);

        // Yüzdeyi orana çevir (örn: 20% → 0.20)
        return BigDecimal.valueOf(indirimYuzdesi).divide(BigDecimal.valueOf(100));
    }
}
