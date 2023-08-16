package sk.library.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.library.model.Book;
import sk.library.model.BookRecord;
import sk.library.model.BooksExport;
import sk.library.repository.BookRepository;

@Service
public class BookExportService {

    @Autowired
    BookRepository bookRepository;

    public BooksExport exportAllBooks() {

        BooksExport export = new BooksExport();

        List<Book> booksList = bookRepository.findAll();

        for(Book book : booksList) {

            BookRecord record = new BookRecord(
                    book.getName(),
                    book.getAuthor().getName() + " " + book.getAuthor().getSurname(),
                    book.getCount());

            export.getBookRecordList().add(record);
        }

        return export;
    }
}
