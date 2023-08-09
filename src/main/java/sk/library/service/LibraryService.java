package sk.library.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.library.model.Book;
import sk.library.model.Library;
import sk.library.repository.LibraryRepository;

@Component
@Transactional
public class LibraryService {

    @Autowired
    LibraryRepository libraryRepository;

    public Optional<Library> findLibraryById(Long id) {
        return libraryRepository.findById(id);
    }

    public Library create(Library library) {
        if (library.getName().length() < 3)
            throw new IllegalArgumentException("Meno kniznice prilis kratke");
        if (library.getAddress() == null || library.getAddress().equals(""))
            throw new IllegalArgumentException("Chyba adresa!");

        return libraryRepository.save(library);
    }

    public List<Library> findByNameContaining(String name) {
        return libraryRepository.findByNameContaining(name);
    }

    public boolean borrowBook(Book book) {
        return true;
    }

    public boolean returnBook(Book book) {
        return true;
    }
}
