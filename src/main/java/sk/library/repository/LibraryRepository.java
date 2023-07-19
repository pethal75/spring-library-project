package sk.library.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import sk.library.model.Library;

@RepositoryRestResource(path = "libraries")
public interface LibraryRepository extends JpaRepository<Library, Long> {


    @RestResource(exported = false)
    @Override
    void delete(Library entity);

    @RestResource(path = "name")
    List<Library> findByNameContaining(String name);
}
