package sk.library.validation;

import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;
import sk.library.model.Library;

@RepositoryEventHandler(Library.class)
@Component
public class LibraryValidator {

    @HandleBeforeSave
    public void onBeforeSave(Library entity) {
        System.out.println("onBeforeSave");

        if (entity.getName().equals("test")) {
            throw new IllegalArgumentException("Chybne meno kniznice");
        }
    }

    @HandleAfterSave
    public void onAfterSave(Library entity) {
        System.out.println("onAfterSave");
    }
}
