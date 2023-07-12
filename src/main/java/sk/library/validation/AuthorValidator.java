package sk.library.validation;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;
import sk.library.model.Author;

@RepositoryEventHandler(Author.class)
@Component
public class AuthorValidator {

    @HandleBeforeCreate
    public void onBeforeCreate(Author entity) {
        System.out.println("onBeforeCreate");

        if (entity.getName() == null || entity.getSurname() == null) {
            throw new IllegalArgumentException("Prazdne meno alebo priezvisko autora!");
        }

        if (entity.getName().equals("") || entity.getSurname().equals("")) {
            throw new IllegalArgumentException("Prazdne meno alebo priezvisko autora!");
        }
    }
}
