package com.dermacon.synonymextractor.service;

import com.dermacon.synonymextractor.model.LocationMapping;
import com.dermacon.synonymextractor.model.MappingLocToSyn;
import com.dermacon.synonymextractor.model.MappingSynToLoc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class SynonymService {

    private final static int MATCHING_THRESHOLD = 3;


    @Autowired
    private CSVService readerService;

    @Autowired
    private ExtractorService extractorService;

    @Autowired
    private ApiService apiService;

    public List<MappingSynToLoc> findSynonyms(String data) {
        List<String> words = extractorService.extractWords(data);
        List<MappingLocToSyn> synonyms = createMap(words);
        return groupDuplicates(synonyms);
    }

    private List<MappingLocToSyn> createMap(List<String> words) {
        List<MappingLocToSyn> apiResponses = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            String currWord = words.get(i);
            Set<String> synonyms = apiService.callApi(currWord);
            log.info("i: {}, syn: {}", i, synonyms.toString());
            LocationMapping currLoc = new LocationMapping(i, currWord);
            apiResponses.add(new MappingLocToSyn(currLoc, synonyms));
        }
        return apiResponses;
    }

    private List<MappingSynToLoc> groupDuplicates(List<MappingLocToSyn> input) {
        List<MappingSynToLoc> groupedElems = new ArrayList<>();
        for (MappingLocToSyn entry : input) {

            MappingSynToLoc match = findCorrespondingTuple(entry, groupedElems);

            if (match == null) {
                Set<LocationMapping> idSet = new HashSet<>();
                idSet.add(entry.getLocationMapping());

                MappingSynToLoc newGroup = MappingSynToLoc.builder()
                        .locationMapping(idSet)
                        .synonyms(entry.getSynonyms())
                        .build();

                groupedElems.add(newGroup);

            } else {
                match.add(entry);
            }

        }
        return groupedElems;
    }

    private MappingSynToLoc findCorrespondingTuple(
            MappingLocToSyn entry,
            List<MappingSynToLoc> groupedElems) {

        for (MappingSynToLoc groupElem : groupedElems) {
            if (setsMatch(groupElem.getSynonyms(), entry.getSynonyms())) {
                return groupElem;
            }
        }

        return null;
    }

    private boolean setsMatch(Set<String> set1, Set<String> set2) {
        int cnt = 0;
        if (set1 == null || set1.isEmpty() || set2 == null || set2.isEmpty()) {
            return false;
        }

        for (String currS1 : set1) {
            if (set2.contains(currS1)) {
                cnt++;
            }
        }
        return cnt >= MATCHING_THRESHOLD;
    }


}
