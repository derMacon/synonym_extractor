package com.dermacon.synonymextractor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LocationMapping {
    private int lineNum;
    private String word;

    @Override
    public String toString() {
        return "(" + lineNum + " - " + word + ")";
    }

}
