package sk.library.service;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import sk.library.service.messages.EmailMessage;
import sk.library.service.messages.NotificationMessage;

@Component
public class NotificationEmailListener implements ApplicationListener<EmailMessage> {

    @Override
    public void onApplicationEvent(EmailMessage event) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException exception) {
            throw new RuntimeException(exception);
        }
        System.out.println("Received Email message - "
                + event.getMessage()
                + " to address "
                + event.getEmailAddress());
    }
}
