package sk.library.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sk.library.model.Book;
import sk.library.repository.BookRepository;
import sk.library.service.messages.EmailMessage;
import sk.library.service.messages.NotificationMessage;
import sk.library.service.messages.SmsMessage;

@Component
@Slf4j
public class ReminderTask {

    @Autowired
    BookRepository repository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Scheduled(fixedRate = 5000)
    public void runTask() {
        log.info("Running reminder task");

        // List<Book> books = repository.findAll();


        EmailMessage msg = new EmailMessage(this,
                "aaa@gmail.com",
                "Book reminder email");
        applicationEventPublisher.publishEvent(msg);

        SmsMessage sms = new SmsMessage(this,
                "0905123456",
                "Book reminder sms");
        applicationEventPublisher.publishEvent(sms);
    }
}
