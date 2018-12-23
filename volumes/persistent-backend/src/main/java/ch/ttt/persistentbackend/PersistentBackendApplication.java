package ch.ttt.persistentbackend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/*
 * Note: /tmp is a directory on the Tomcat
 *       Do not point to local directories in Spring Boot apps
 */
@RestController
@RequestMapping("/api")
@SpringBootApplication
public class PersistentBackendApplication {
    private static final Logger log = LoggerFactory.getLogger(PersistentBackendApplication.class);
    private final static Path STORAGE_PATH = Paths.get("/tmp/entries.txt");
    private List<String> entries = new ArrayList<>();

    public static void main(String[] args) {
        SpringApplication.run(PersistentBackendApplication.class, args);
    }

    @Bean
    public CommandLineRunner init() {
        return args -> {
            if (!Files.exists(STORAGE_PATH)) {
                Files.createFile(STORAGE_PATH);
            }
        };
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    public String create() {
        String entry = UUID.randomUUID().toString();
        entries.add(entry);
        log.info("created entry {}", entry);
        write();
        return "created entry " + entry;
    }

    @RequestMapping(value = "/entries", method = RequestMethod.GET, produces = "text/plain")
    @ResponseBody
    public String findAll() {
        entries = read();
        StringBuilder result = new StringBuilder("Entries (" + entries.size() + "): \n\n");
        entries.forEach(entry -> result.append(entry).append("\n"));
        log.info("entries: {}", entries);
        return result.toString();
    }

    private List<String> read() {
        try {
            return Files.readAllLines(STORAGE_PATH);
        } catch (IOException e) {
            log.error("error: {}", e.getMessage());
            return null;
        }
    }

    private void write() {
        try (PrintWriter printWriter = new PrintWriter(STORAGE_PATH.toFile())) {
            entries.forEach(printWriter::println);
            log.info("saved entries: {}", entries);
        } catch (IOException e) {
            log.error("error: {}", e.getMessage());
        }
    }
}

