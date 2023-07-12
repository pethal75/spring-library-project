package sk.library.model;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "AuthorSurnames", types = { Author.class })
interface AuthorNameProjection {
    String getSurname();
}
