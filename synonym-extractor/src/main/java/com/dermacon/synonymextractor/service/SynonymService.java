package com.dermacon.synonymextractor.service;

import com.dermacon.synonymextractor.model.MappingIdToSyn;
import com.dermacon.synonymextractor.model.MappingSynToId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class SynonymService {

    private final static int MATCHING_THRESHOLD = 3;


    @Autowired
    private CSVReaderService readerService;

    @Autowired
    private ExtractorService extractorService;

    @Autowired
    private ApiService apiService;

    public void findSynonyms(String data, String outputPath) {
        List<String> words = extractorService.extractWords(data);
        List<MappingIdToSyn> synonyms = createMap(words);
        List<MappingSynToId> grouped = groupDuplicates(synonyms);

    }

    private List<MappingIdToSyn> createMap(List<String> words) {
        List<MappingIdToSyn> apiResponses = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            String currWord = words.get(i);
            Set<String> synonyms = apiService.callApi(currWord);
            log.info("i: {}, syn: {}", i, synonyms.toString());
            apiResponses.add(new MappingIdToSyn(i, synonyms));
        }
        return apiResponses;
    }

    private List<MappingSynToId> groupDuplicates(List<MappingIdToSyn> input) {
        List<MappingSynToId> groupedElems = new ArrayList<>();
        for (MappingIdToSyn entry : input) {

            MappingSynToId match = findCorrespondingTuple(entry, groupedElems);

            if (match == null) {
                Set<Integer> idSet = new HashSet<>();
                idSet.add(entry.getId());

                MappingSynToId newGroup = MappingSynToId.builder()
                        .ids(idSet)
                        .synonyms(entry.getSynonyms())
                        .build();

                groupedElems.add(newGroup);

            } else {
                match.add(entry);
            }

        }
        return groupedElems;
    }

    private MappingSynToId findCorrespondingTuple(
            MappingIdToSyn entry,
            List<MappingSynToId> groupedElems) {

        for (MappingSynToId groupElem : groupedElems) {
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
