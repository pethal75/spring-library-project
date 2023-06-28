package sk.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sk.library.model.Book;
import sk.library.model.Library;

public interface BookRepository extends JpaRepository<Book, Long> {
}
