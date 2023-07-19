package sk.library.controller;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
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
import sk.library.model.Library;
import sk.library.repository.LibraryRepository;

@RestController
@RequestMapping(value = "/libraries", produces = MediaType.APPLICATION_JSON_VALUE)
public class LibraryController {

    @Autowired
    LibraryRepository libraryRepository;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Library> getLibrary(@PathVariable("id") Long id) {

        Optional<Library> libraryOptional = libraryRepository.findById(id);

        return ResponseEntity.ok(libraryOptional.get());
    }

    @PostMapping(value = "/")
    public ResponseEntity<Library> createLibrary(@RequestBody Library library) {

        Library libraryCreated = libraryRepository.save(library);

        return ResponseEntity.ok(libraryCreated);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<Library>> searchLibraries(@RequestParam(name = "nameQuery", required = false) String name) {

        List<Library> libraries = libraryRepository.findByNameContaining(name);

        return ResponseEntity.ok(libraries);
    }

    @GetMapping(value = "/test")
    public Library testEndpoint() {

        Library library = new Library();
        library.setName("TEST");
        return library;
    }

    @GetMapping(value = "/testException500")
    public void testEndpointException500() {

        throw new IllegalStateException("TEST");
    }

    @GetMapping(value = "/testException400")
    public void testEndpointException400() {

        throw new IllegalArgumentException("TEST");
    }
    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<String> handleException() {
        return ResponseEntity.badRequest().body("Zle parametre");
    }
}
