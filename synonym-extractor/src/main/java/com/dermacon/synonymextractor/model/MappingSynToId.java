package com.dermacon.synonymextractor.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class MappingSynToId {
    private Set<String> synonyms;
    private Set<Integer> ids;

    public void add(MappingIdToSyn elem) {
        synonyms.addAll(elem.getSynonyms());
        ids.add(elem.getId());
    }
}
