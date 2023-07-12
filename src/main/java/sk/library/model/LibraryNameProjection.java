package sk.library.model;

import org.springframework.data.rest.core.config.Projection;
import sk.library.model.Library;

@Projection(name = "LibraryName", types = { Library.class })
interface LibraryNameProjection {
    String getName();
}
