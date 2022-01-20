package com.dermacon.synonymextractor;

import com.dermacon.synonymextractor.service.CSVReaderService;
import com.dermacon.synonymextractor.service.SynonymService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SynonymExtractorApplication implements CommandLineRunner {

	@Autowired
	private SynonymService synonymService;

	@Autowired
	private CSVReaderService readerService;

	public static void main(String[] args) {
		SpringApplication.run(SynonymExtractorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("started");

		if (args.length == 2) {
			String data = readerService.read(args[0]);
			synonymService.findSynonyms(data, args[1]);
		} else {
			log.error("input and output path must be set in args");
		}
		log.info("ended");
	}
}
