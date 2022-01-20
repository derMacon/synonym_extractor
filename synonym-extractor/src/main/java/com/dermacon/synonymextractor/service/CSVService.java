package com.dermacon.synonymextractor.service;

import com.dermacon.synonymextractor.model.LocationMapping;
import com.dermacon.synonymextractor.model.MappingSynToLoc;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class CSVService {

    @SneakyThrows
    public String read(String inputPath) {
        log.info("input path: {}", inputPath);
        File file = new File(inputPath);
        assert file.exists();
        return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
    }

    @SneakyThrows
    public void write(List<MappingSynToLoc> output, String outputPath) {
        String csvContent = generateCsvContent(output);
        File outFile = new File(outputPath);
        FileUtils.writeStringToFile(outFile, csvContent);
    }

    private String generateCsvContent(List<MappingSynToLoc> output) {
        StringBuilder strb = new StringBuilder();
        for (MappingSynToLoc curr : output) {
            strb.append(generateCsvContent(curr) + "\n");
        }
        return strb.toString();
    }

    private String generateCsvContent(MappingSynToLoc elem) {

        // synonyms
        StringBuilder synonyms = new StringBuilder();
        Iterator<String> synIt = elem.getSynonyms().iterator();
        while (synIt.hasNext()) {
            synonyms.append(synIt.next());
            if (synIt.hasNext()) {
                synonyms.append(",");
            }
        }

        // location mapping
        StringBuilder loc = new StringBuilder();
        Iterator<LocationMapping> locIt = elem.getLocationMapping().iterator();
        while (locIt.hasNext()) {
            loc.append(locIt.next().toString());
            if (locIt.hasNext()) {
                loc.append(",");
            }
        }

        return "\"" + synonyms.toString() + "\",\"" + loc.toString() + "\"";
    }

}
