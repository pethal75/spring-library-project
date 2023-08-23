package sk.library.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class AuthorRecord {
    protected String name;

    protected Long bookCount;
}
