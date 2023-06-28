package sk.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sk.library.model.Library;

@RepositoryRestResource(path = "libraries")
public interface LibraryRepository extends JpaRepository<Library, Long> {
}
