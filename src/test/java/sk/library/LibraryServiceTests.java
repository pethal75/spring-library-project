package sk.library;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sk.library.model.Book;
import sk.library.model.Library;
import sk.library.repository.BookRepository;
import sk.library.service.LibraryService;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class LibraryServiceTests {

    @Autowired
    LibraryService libraryService;
    @Autowired
    BookRepository bookRepository;

    @Test
    void testFindLibraryOk() {
        Optional<Library> libraryOptional = libraryService.findLibraryById(2L);

        assertThat(libraryOptional).isPresent();
        assertThat(libraryOptional.get().getName()).isEqualTo("Kniznica Ruzinov");
    }

    @Test
    void testFindLibraryNotFound() {
        Optional<Library> libraryOptional = libraryService.findLibraryById(100L);

        assertThat(libraryOptional).isEmpty();
    }

    @Test
    void testCreateLibrary() {
        Library library = new Library();
        library.setName("nova kniznica");
        library.setAddress("adresa, 12345");

        library = libraryService.create(library);

        assertThat(library.getId()).isNotNull();
    }

    @Test
    void testCreateLibraryMissingAddress() {
        Library library = new Library();
        library.setName("nova kniznica");

        assertThatThrownBy(() -> libraryService.create(library))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testCreateLibraryShortName() {
        Library library = new Library();
        library.setName("xy");
        library.setAddress("adresa");

        assertThatThrownBy(() -> libraryService.create(library))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testBorrowBookSuccess() {
        assertThat(libraryService.borrowBook(2L)).isTrue();
        assertThat(libraryService.borrowBook(2L)).isTrue();
        assertThat(libraryService.borrowBook(2L)).isTrue();
        assertThat(libraryService.borrowBook(2L)).isFalse();
    }
    @Test
    void testBorrowBookSuccessUniversal() {

        List<Book> randomBooks = bookRepository.findAll();
        Book randomBook = randomBooks.stream()
                .filter(book -> book.getCount() > 0)
                .findFirst().orElse(null);

        assertThat(randomBook).isNotNull();

        Long countBefore = randomBook.getCount();

        assertThat(
                libraryService.borrowBook(randomBook.getId())
        ).isTrue();

        Book bookAfter = bookRepository.findById(randomBook.getId()).get();
        Long countAfter = bookAfter.getCount();

        assertThat(countBefore - 1).isEqualTo(countAfter);
    }

    @Test
    void testBorrowBookNotExisting() {
        assertThat(libraryService.borrowBook(2000L)).isFalse();
    }

    @Test
    void testReturnBookSuccess() {

        Book randomBook = bookRepository.findAll().iterator().next();

        assertThat(randomBook).isNotNull();

        System.out.println("Book before " + randomBook.getName() + " " + randomBook.getCount());

        Long countBefore = randomBook.getCount();

        assertThat(
                libraryService.returnBook(randomBook.getId())
        ).isTrue();

        Book bookAfter = bookRepository.findById(randomBook.getId()).get();

        System.out.println("Book after " + bookAfter.getName() + " " + bookAfter.getCount());

        Long countAfter = bookAfter.getCount();

        assertThat(countBefore + 1).isEqualTo(countAfter);
    }
}
