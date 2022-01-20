package com.dermacon.synonymextractor.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class ApiService {

    private final static String ELEM_NAME = "synsets";

    @Value("${api.url}")
    private String urlTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    public Set<String> callApi(String word) {
        Set<String> out = new HashSet<>();

        String url = String.format(urlTemplate, word);
        String resp = restTemplate.getForObject(url, String.class);

        ObjectNode node = new ObjectMapper().readValue(resp, ObjectNode.class);
        if (node.has(ELEM_NAME)) {
            JsonNode jsonNode = node.get(ELEM_NAME);
            log.info("input: " + jsonNode);
            JsonNode synonyms = objectMapper.readValue(jsonNode.get(0).toString(), ObjectNode.class).get("terms").get(0);
        }

        out.add(word);
        return out;
    }

}
