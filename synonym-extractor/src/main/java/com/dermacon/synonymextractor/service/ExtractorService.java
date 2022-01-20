package com.dermacon.synonymextractor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ExtractorService {

    public List<String> extractWords(String input) {
//        log.info("input: {}", input);

        List<String> out = new ArrayList<>();
        for (String line : input.split("\n")) {
            out.add(line.split(",")[0]);
        }
        out.remove(0); // remove title
        return out;
    }

}
