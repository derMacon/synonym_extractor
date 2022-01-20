package com.dermacon.synonymextractor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class MappingIdToSyn {

    private int id;
    private Set<String> synonyms;

}
