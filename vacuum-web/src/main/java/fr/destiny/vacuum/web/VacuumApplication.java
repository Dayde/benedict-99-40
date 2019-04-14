package fr.destiny.vacuum.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {VacuumApplication.class})
public class VacuumApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(VacuumApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(VacuumApplication.class, args);
    }

    @Override
    public void run(String... args) {
        LOGGER.info("Application started.");
    }
}
