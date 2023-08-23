package sk.library;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import sk.library.repository.LibraryRepository;

@SpringBootApplication
@EnableWebMvc
@Slf4j
public class LibraryApplication {

	@Autowired
	protected LibraryRepository libraryRepository;

	public static void main(String[] args) {

		SpringApplication.run(LibraryApplication.class, args);
	}
}
