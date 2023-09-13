package sk.library.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sk.library.model.Book;
import sk.library.repository.BookRepository;

@Component
@Slf4j
public class ReminderTask {

    @Autowired
    BookRepository repository;

    @Scheduled(fixedRate = 3000)
    public void runTask() {
        log.info("Running task");


        List<Book> books = repository.findAll();

        for(Book book : books) {
            if (book.getCount() > 0)
                log.info("Checking book " + book.getName());
        }

        books.stream()
            .filter(book -> book.getCount() > 0)
            .forEach(book -> log.info("Checking book " + book.getName()));
    }
}
