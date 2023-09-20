package sk.library.service.messages;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EmailMessage extends ApplicationEvent {
    private String emailAddress;
    private String message;

    public EmailMessage(Object source, String emailAddress, String message) {
        super(source);
        this.message = message;
        this.emailAddress = emailAddress;
    }
}
