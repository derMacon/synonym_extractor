package com.dermacon.synonymextractor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SynonymService {


    @Autowired
    private CSVReaderService readerService;

    @Autowired
    private ExtractorService extractorService;

    @Autowired
    private ApiService apiService;

    public void findSynonyms(String data, String outputPath) {
        List<String> words = extractorService.extractWords(data);

        Map<Integer, Set<String>> apiResponses = new HashMap<>();
        for (int i = 0; i < words.size(); i++) {
            String currWord = words.get(i);
            Set<String> synonyms = apiService.callApi(currWord);
            apiResponses.put(i, synonyms);
        }

        Map<Set<String>, Set<Integer>> synonymGroups = new HashMap<>();

    }


}
