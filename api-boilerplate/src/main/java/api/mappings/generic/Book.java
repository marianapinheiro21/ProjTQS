package api.mappings.generic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Integer id;
    private String title;
    private String author;
    private String publisher;
    private Integer editionYear;
    private String edition;
    private String description;
    private String isbn;
    private BookStatus status;

    public enum BookStatus {
        AVAILABLE,
        NOT_AVAILABLE,
        RESERVED
    }
}