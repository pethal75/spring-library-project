package sk.library;

import sk.library.model.Author;
import sk.library.model.Book;
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

			List<Author> authors = repository.findAll();

			for (Author author : authors) {
				System.out.println("AUTHOR: " + author.getName() + " " + author.getSurname());

				for(Book book : author.getBooks())
					System.out.println("  BOOK: " + book.getName());

				System.out.println();
			}

			Author author1 = new Author();

			author1.setName("Mark");
			author1.setSurname("Twain2");

			repository.save(author1);

			author1.setSurname("Twain3");
			repository.save(author1);
		};
	}
}
