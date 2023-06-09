package sk.library;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sk.library.model.Author;
import sk.library.model.Library;
import sk.library.repository.AuthorRepository;
import sk.library.repository.LibraryRepository;

@SpringBootApplication
public class LibraryApplication {

	@Autowired
	protected LibraryRepository libraryRepository;

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}


	@Bean
	public CommandLineRunner startup() {
		return (args) -> {
			System.out.println("*** Library application ***");

			for (Library library : libraryRepository.findAll()) {
				System.out.println("LIBRARY: " + library.getName() + " " + library.getAddress());
			}
		};
	}
}





			/*List<Author> listAuthors = repository.findAll();
			for (Author author : listAuthors) {
				System.out.println("AUTHOR: " + author.getName() + " " + author.getSurname());
			}*/


