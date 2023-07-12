package sk.library.repository;


import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import sk.library.dto.AuthorSurname;
import sk.library.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findAllByNameOrSurname(String name, String surname);

    @Query("select a from Author a where lower(a.name) like %:searchString% or lower(a.surname) like %:searchString%")
    List<Author> findAllByAnyName(@Param("searchString") String search, Pageable page);

    @Query("select a from Author a where lower(a.name) like %:searchString% or lower(a.surname) like %:searchString%")
    List<AuthorSurname> findSurnamesByAnyName(@Param("searchString") String search);

    @Query("select a from Author a where (select count(*) from Book b where b.author.id = a.id) >= :count")
    List<Author> findAllByBookCount(@Param("count") int count);

    List<AuthorSurname> findAllByOrderByName();

    @RestResource(exported = false)
    @Override
    void delete(Author author);
}
