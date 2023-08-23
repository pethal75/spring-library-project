package sk.library.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.library.dto.AuthorRecord;
import sk.library.dto.AuthorsExport;
import sk.library.model.Author;
import sk.library.repository.AuthorRepository;

@Service
public class AuthorExportService {

    @Autowired
    AuthorRepository authorRepository;

    public AuthorsExport exportAllAuthors() {

        AuthorsExport authorsExport = new AuthorsExport();

        List<Author> authors = authorRepository.findAll();

        for(Author author : authors) {
            AuthorRecord record = AuthorRecord.builder()
                    .name(author.getName() + " " + author.getSurname())
                    .bookCount((long) author.getBooks().size())
                    .build();

            authorsExport.getListAuthors().add(record);
        }

        return authorsExport;
    }
}
