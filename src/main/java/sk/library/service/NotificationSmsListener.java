package sk.library.service;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import sk.library.service.messages.SmsMessage;

@Component
public class NotificationSmsListener implements ApplicationListener<SmsMessage> {

    @Override
    public void onApplicationEvent(SmsMessage event) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Received SMS message - "
                + event.getMessage()
                + " to number "
                + event.getPhoneNumber());
    }
}
