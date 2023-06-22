package sk.library;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sk.library.model.Library;
import sk.library.repository.LibraryRepository;

@SpringBootTest
class LibraryRepositoryTests {

    @Autowired
    protected LibraryRepository libraryRepository;

    @Test
    void testFindAll() {
        List<Library> libraryList = libraryRepository.findAll();

        assertThat(libraryList).hasSize(10);
    }

    @Test
    void testCreateLibrary() {
        long count = libraryRepository.count();

        Library library = new Library();
        library.setName("New library");
        libraryRepository.save(library);

        long count2 = libraryRepository.count();

        assertThat(count2).isEqualTo(count + 1);
    }

    @Test
    void testDeleteLibrary() {
        long count = libraryRepository.count();

        libraryRepository.deleteById(2L);

        long count2 = libraryRepository.count();

        assertThat(count2).isEqualTo(count - 1);
    }
}
