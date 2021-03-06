package de.phip1611.hockeyligamanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ConfigurationPropertiesScan
public class HockeyLigaManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HockeyLigaManagerApplication.class, args);
	}

}
