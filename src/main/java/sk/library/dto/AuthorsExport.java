package sk.library.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class AuthorsExport {
    List<AuthorRecord> listAuthors = new ArrayList<>();
}
