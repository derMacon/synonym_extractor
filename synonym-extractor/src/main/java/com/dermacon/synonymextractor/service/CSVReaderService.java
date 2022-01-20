package com.dermacon.synonymextractor.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class CSVReaderService {

    @SneakyThrows
    public String read(String inputPath) {
        log.info("input path: {}", inputPath);
        File file = new File(inputPath);
        assert file.exists();
        return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
    }

}
