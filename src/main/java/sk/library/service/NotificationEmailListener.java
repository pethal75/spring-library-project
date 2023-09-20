package sk.library.service;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import sk.library.service.messages.NotificationMessage;

@Component
public class NotificationEmailListener implements ApplicationListener<NotificationMessage> {

    @Override
    public void onApplicationEvent(NotificationMessage event) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Received Email message - " + event.getMessage());
    }
}