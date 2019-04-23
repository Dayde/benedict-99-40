package fr.destiny.benedict.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {BenedictApplication.class})
public class BenedictApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(BenedictApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BenedictApplication.class, args);
    }

    @Override
    public void run(String... args) {
        LOGGER.info("Application started.");
    }
}
