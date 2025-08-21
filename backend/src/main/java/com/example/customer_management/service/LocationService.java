package com.example.customer_management.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private static final String BASE = "https://turkiyeapi.dev/api/v1";

    // cache provinces for 24h
    @Cacheable("provinces")
    public List<String> getProvinces() {
        String resp = restTemplate.getForObject(BASE + "/provinces", String.class);
        try {
            JsonNode root = mapper.readTree(resp);
            JsonNode data = root.path("data");
            List<String> names = new ArrayList<>();
            if (data.isArray()) {
                for (JsonNode n : data) names.add(n.path("name").asText());
            }
            return names;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch provinces", e);
        }
    }

    // cache districts per-province
    @Cacheable(value = "districts", key = "#province")
    public List<String> getDistricts(String province) {
        try {
            String url = BASE + "/districts?province=" + URLEncoder.encode(province, StandardCharsets.UTF_8);
            String resp = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(resp);
            JsonNode data = root.path("data");
            List<String> names = new ArrayList<>();
            if (data.isArray()) {
                for (JsonNode n : data) names.add(n.path("name").asText());
            }
            return names;
        } catch (Exception ex) {
            // fallback: try to find districts inside /provinces response
            return getDistrictsFromProvincesFallback(province);
        }
    }

    // fallback method that searches the provinces call for nested districts
    private List<String> getDistrictsFromProvincesFallback(String province) {
        try {
            String resp = restTemplate.getForObject(BASE + "/provinces", String.class);
            JsonNode root = mapper.readTree(resp);
            for (JsonNode prov : root.path("data")) {
                if (province.equalsIgnoreCase(prov.path("name").asText())) {
                    JsonNode districts = prov.path("districts");
                    List<String> names = new ArrayList<>();
                    if (districts.isArray()) {
                        for (JsonNode d : districts) names.add(d.path("name").asText());
                    }
                    return names;
                }
            }
        } catch (Exception e) { /* swallow and return empty */ }
        return Collections.emptyList();
    }
}
