package sk.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.library.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
