package ch.ttt.dbbackend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*
 * DB access does only work in docker environment
 * DB server is defined in docker compose
 */
@RestController
@RequestMapping("/api")
@SpringBootApplication
public class DbBackendApplication {
    private static final Logger log = LoggerFactory.getLogger(DbBackendApplication.class);

    @Autowired
    private EntryRepository entryRepository;

    public static void main(String[] args) {
        SpringApplication.run(DbBackendApplication.class, args);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    public String create() {
        final Entry entry = new Entry(UUID.randomUUID().toString());
        log.info("created entry {}", entry);
        entryRepository.save(entry);
        return "created entry " + entry;
    }

    @RequestMapping(value = "/entries", method = RequestMethod.GET, produces = "text/plain")
    @ResponseBody
    public String findAll() {
        Iterable<Entry> entries = entryRepository.findAll();
        log.info("found entries: {}", entries);

        StringBuilder result = new StringBuilder("Entries: \n\n");
        entries.forEach(entry -> result.append(entry).append("\n"));
        return result.toString();
    }
}

