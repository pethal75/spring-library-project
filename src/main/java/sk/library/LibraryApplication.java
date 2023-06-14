package sk.library;

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

			Pageable page = PageRequest.of(2,5, Sort.by("name"));

			List<Author> authors = repository.findAllByAnyName("k", page);

			for (Author author : authors) {
				System.out.println("AUTHOR: " + author.getName() + " " + author.getSurname());

				for(Book book : author.getBooks()) {
					System.out.println("  BOOK: " + book.getName());

					for(Review review : book.getReviews())
						System.out.println("    REVIEW: " + review.getReviewText());

				}

				System.out.println();
			}
		};
	}
}
