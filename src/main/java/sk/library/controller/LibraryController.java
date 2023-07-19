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
@RequestMapping(value = "/api/v1/libraries", produces = MediaType.APPLICATION_JSON_VALUE)
public class LibraryController {

    @Autowired
    LibraryRepository libraryRepository;



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

        if (id == null || id < 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Library> libraryOptional = libraryRepository.findById(id);

        if (!libraryOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(libraryOptional.get());
    }

    @PostMapping(value = "/")
    public ResponseEntity<Library> createLibrary(@RequestBody Library library) {

        Library libraryCreated = libraryRepository.save(library);

        return ResponseEntity.ok(libraryCreated);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<Library>> searchLibraries(
            @RequestParam(name = "nameQuery", required = false) String name) {

        List<Library> libraries = libraryRepository.findByNameContaining(name);

        return ResponseEntity.ok(libraries);
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
