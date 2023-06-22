package sk.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.library.model.Library;

public interface LibraryRepository extends JpaRepository<Library, Long> {
}
