package sk.library.service.messages;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
public class NotificationMessage extends ApplicationEvent {
    private String message;

    public NotificationMessage(Object source, String message) {
        super(source);
        this.message = message;
    }
}
