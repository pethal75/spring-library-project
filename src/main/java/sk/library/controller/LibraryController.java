package sk.library.controller;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sk.library.dto.AuthorsExport;
import sk.library.model.BooksExport;
import sk.library.model.Library;
import sk.library.service.AuthorExportService;
import sk.library.service.BookExportService;
import sk.library.service.LibraryService;

@RestController
@RequestMapping(value = "/api/v1/libraries", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class LibraryController {

    @Autowired
    LibraryService libraryService;

    @Autowired
    BookExportService bookExportService;

    @Autowired
    AuthorExportService authorExportService;

    @GetMapping(value = "/test/{id}")
    public ResponseEntity<String> test(@PathVariable("id") Long id,
                             @RequestParam(value = "filter", required = false) String filter,
                             @RequestBody(required = false) String body)  {

        if (filter == null)
            return ResponseEntity.badRequest().body("Chybne data");

        if (body == null)
            return ResponseEntity.badRequest().body("Chyba request body");

        return ResponseEntity.ok("OK");
    }



    @GetMapping(value = "/{id}")
    public ResponseEntity<Library> getLibrary(@PathVariable("id") Long id) {

        log.debug("findLibraryById {}", id);

        if (id == null || id < 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Library> libraryOptional = libraryService.findLibraryById(id);

        if (libraryOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(libraryOptional.get());
    }

    @PostMapping(value = "/")
    public ResponseEntity<Library> createLibrary(@RequestBody Library library) {

        Library libraryCreated = libraryService.create(library);

        return ResponseEntity.ok(libraryCreated);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<Library>> searchLibraries(
            @RequestParam(name = "nameQuery", required = false) String name) {

        List<Library> libraries = libraryService.findByNameContaining(name);

        return ResponseEntity.ok(libraries);
    }

    @PostMapping(value = "/borrowBook/{id}")
    public boolean borrowBook(@PathVariable("id") Long id) {
        return libraryService.borrowBook(id);
    }

    @PostMapping(value = "/returnBook/{id}")
    public boolean returnBook(@PathVariable("id") Long id) {

        return libraryService.returnBook(id);
    }

    @GetMapping(value = "/exportAllBooks")
    public BooksExport exportBooks() {

        log.debug("DEBUGGING");
        log.error("ERROR");

        return bookExportService.exportAllBooks();
    }

    @GetMapping(value = "/exportAllAuthors")
    public AuthorsExport exportAuthors() {

        return authorExportService.exportAllAuthors();
    }
}
