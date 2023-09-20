package sk.library.service.messages;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SmsMessage extends ApplicationEvent {
    private String phoneNumber;
    private String message;

    public SmsMessage(Object source, String phoneNumber, String message) {
        super(source);
        this.message = message;
        this.phoneNumber = phoneNumber;
    }
}
