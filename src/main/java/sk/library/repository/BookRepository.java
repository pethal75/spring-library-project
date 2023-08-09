package sk.library.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sk.library.model.Book;

@RepositoryRestResource(path = "books")
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByCountGreaterThan(Long count);
}
