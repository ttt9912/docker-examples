package ch.ttt.simplebackenddockerplugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@SpringBootApplication
public class SimpleBackendDockerPluginApplication {
	private static final Logger log = LoggerFactory.getLogger(SimpleBackendDockerPluginApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SimpleBackendDockerPluginApplication.class, args);
	}

	private final List<String> entries = new ArrayList<>();

	@RequestMapping(value = "/createEntry", method = RequestMethod.GET)
	@ResponseBody
	public String createEntry() {
		String entry = UUID.randomUUID().toString();
		entries.add(entry);
		log.info("added random entry: {}", entry);
		return entry;
	}

	@RequestMapping(value = "/entries", method = RequestMethod.GET, produces = "text/plain")
	@ResponseBody
	public String getEntries() {
		StringBuilder result = new StringBuilder("Entries (" + entries.size() + "): \n\n");
		for (String entry : entries) {
			result.append(entry).append("\n");
		}

		log.info("returning all entries: {}", entries);
		return result.toString();
	}

}

