package sk.library;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import sk.library.dto.AuthorSurname;
import sk.library.model.Author;
import sk.library.model.Book;
import sk.library.model.Review;
import sk.library.repository.AuthorRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@Bean
	public CommandLineRunner startup(AuthorRepository repository) {
		return (args) -> {
			System.out.println("*** DEMO JPA ***");

			List<Author> listAuthors = repository.findAll();

			for (Author author : listAuthors) {
				System.out.println("AUTHOR: " + author.getName() + " " + author.getSurname());
			}
		};
	}
}
