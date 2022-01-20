package com.dermacon.synonymextractor.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class MappingSynToLoc {
    private Set<String> synonyms;
    private Set<LocationMapping> locationMapping;

    public void add(MappingLocToSyn elem) {
        synonyms.addAll(elem.getSynonyms());
        locationMapping.add(elem.getLocationMapping());
    }
}
