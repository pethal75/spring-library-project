package sk.library.service;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import sk.library.service.messages.NotificationMessage;

@Component
public class NotificationListenerService implements ApplicationListener<NotificationMessage> {

    @Override
    public void onApplicationEvent(NotificationMessage event) {
        System.out.println("Received notification message - " + event.getMessage());
    }
}
