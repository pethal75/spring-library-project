package sk.library;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sk.library.model.BooksExport;
import sk.library.repository.BookRepository;
import sk.library.service.BookExportService;

@SpringBootTest
class BookExportServiceTests {

    @Autowired
    BookExportService bookExportService;
    @Autowired
    BookRepository bookRepository;

    @Test
    void testExportSuccess() {
        var export = bookExportService.exportAllBooks();

        var count = bookRepository.count();

        assertThat(export.getBookRecordList()).hasSize((int) count);

        System.out.println(export);
    }
}
