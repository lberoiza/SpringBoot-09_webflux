package com.lab.springboot.webflux.app;

import com.lab.springboot.webflux.app.utils.DataImporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner {

	private final DataImporter dataImporter;

	@Autowired
	public SpringBootWebfluxApplication(DataImporter dataImporter) {
		this.dataImporter = dataImporter;
	}


	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) {
		dataImporter.importInitialData();
	}
}
