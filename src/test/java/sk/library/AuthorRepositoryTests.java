package sk.library;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import sk.library.dto.AuthorSurname;
import sk.library.model.Author;
import sk.library.repository.AuthorRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthorRepositoryTests {

	@Autowired
	AuthorRepository repository;

	@Test
	void testLoadAuthors() {

		List<Author> authorList = repository.findAll();

		assertThat(authorList).isNotEmpty();
	}

	@Test
	void testLoadAuthorsByName() {

		List<Author> authorList = repository.findAllByNameOrSurname("Ernest", "Gogol");

		assertThat(authorList).isNotEmpty();
	}

	@Test
	void testLoadAuthorsBySearchString() {

		Pageable page = PageRequest.of(0,5, Sort.by("name"));

		List<Author> authorList = repository.findAllByAnyName("ka", page);

		assertThat(authorList).hasSize(2);
	}

	@Test
	void testLoadAuthorsByBookCount() {

		List<Author> authorList = repository.findAllByBookCount(4);

		assertThat(authorList).hasSize(1);
	}

	@Test
	void testLoadAuthorsPageable() {

		Pageable page = PageRequest.of(0,5, Sort.by("name"));

		Page<Author> authorList = repository.findAll(page);

		assertThat(authorList).hasSize(5);
	}

	@Test
	void testLoadAuthorsNames() {

		List<AuthorSurname> authorList = repository.findAllByOrderByName();

		assertThat(authorList).isNotEmpty();
	}

	/*@Test
	void testDataIntegrityException() {

		List<Author> authorList = repository.findAll();

		Author author = new Author();
		author.setName(authorList.get(0).getName());
		author.setSurname(authorList.get(0).getSurname());

		assertThrows(DataIntegrityViolationException.class, () -> repository.save(author));
	}*/
}
