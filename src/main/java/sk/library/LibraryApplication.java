package sk.library;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import sk.library.repository.LibraryRepository;

@SpringBootApplication
@EnableWebMvc
@Slf4j
//@EnableScheduling
public class LibraryApplication {

	@Autowired
	protected LibraryRepository libraryRepository;

	@Value("${adresar}")
	String adresar;

	@PostConstruct
	public void postCons() {
		log.debug(adresar);
	}

	public static void main(String[] args) {

		SpringApplication.run(LibraryApplication.class, args);
	}
}
