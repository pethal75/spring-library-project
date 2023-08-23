package sk.library.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.library.model.Book;
import sk.library.model.Library;
import sk.library.repository.BookRepository;
import sk.library.repository.LibraryRepository;

@Component
@Transactional
@Slf4j
public class LibraryService {

    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    BookRepository bookRepository;

    public Optional<Library> findLibraryById(Long id) {

        log.debug("findLibraryById {}", id);

        return libraryRepository.findById(id);
    }

    public Library create(Library library) throws IllegalArgumentException {
        if (library.getName() == null || library.getName().length() < 3)
            throw new IllegalArgumentException("Meno kniznice prilis kratke");
        if (library.getAddress() == null || library.getAddress().equals(""))
            throw new IllegalArgumentException("Chyba adresa!");

        return libraryRepository.save(library);
    }

    public List<Library> findByNameContaining(String name) {
        return libraryRepository.findByNameContaining(name);
    }

    /**
     * Borrows book from storage. If book count is empty, return false.
     *
     * @param idBook id of borrowed book
     *
     * @return  true if book was borrowed
     *          false if book is not in the storage
     */
    public boolean borrowBook(Long idBook) {

        // Find book in database
        Optional<Book> book = bookRepository.findById(idBook);

        if (book.isEmpty())
            return false;

        // Check book count > 0
        if (book.get().getCount() == 0)
            return false;

        // Decrease book count
        Long currentCount = book.get().getCount();

        book.get().setCount( currentCount - 1 );

        bookRepository.save(book.get());

        return true;
    }

    public boolean returnBook(Long idBook) {

        // Find book in database
        Optional<Book> book = bookRepository.findById(idBook);

        if (book.isEmpty())
            return false;

        // Increase book count
        Long currentCount = book.get().getCount();

        book.get().setCount( currentCount + 1 );

        bookRepository.save(book.get());

        return true;
    }
}
