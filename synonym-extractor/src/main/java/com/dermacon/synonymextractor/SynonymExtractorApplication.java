package com.dermacon.synonymextractor;

import com.dermacon.synonymextractor.model.MappingSynToLoc;
import com.dermacon.synonymextractor.service.CSVService;
import com.dermacon.synonymextractor.service.SynonymService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@Slf4j
public class SynonymExtractorApplication implements CommandLineRunner {

	@Autowired
	private SynonymService synonymService;

	@Autowired
	private CSVService csvService;

	public static void main(String[] args) {
		SpringApplication.run(SynonymExtractorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("started");

		if (args.length == 2) {

			String data = csvService.read(args[0]);
			List<MappingSynToLoc> output = synonymService.findSynonyms(data);
			csvService.write(output, args[1]);

		} else {
			log.error("input and output path must be set in args");
		}
		log.info("ended");
	}
}
