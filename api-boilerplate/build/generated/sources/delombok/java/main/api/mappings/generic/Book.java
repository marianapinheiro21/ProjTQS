package api.mappings.generic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.time.LocalDate;

public class Book {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("author")
    private String author;
    @JsonProperty("publisher")
    private String publisher;
    @JsonProperty("editionYear")
    private Integer editionYear;
    @JsonProperty("edition")
    private String edition;
    @JsonProperty("description")
    private String description;
    @JsonProperty("isbn")
    private String isbn;
    @JsonProperty("status")
    private String status;


    public static class BookBuilder {
        private Integer id;
        private String title;
        private String author;
        private String publisher;
        private Integer editionYear;
        private String edition;
        private String description;
        private String isbn;
        private String status;

        BookBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("id")
        public Book.BookBuilder id(final Integer id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("title")
        public Book.BookBuilder title(final String title) {
            this.title = title;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("author")
        public Book.BookBuilder author(final String author) {
            this.author = author;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("publisher")
        public Book.BookBuilder publisher(final String publisher) {
            this.publisher = publisher;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("editionYear")
        public Book.BookBuilder editionYear(final Integer editionYear) {
            this.editionYear = editionYear;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("edition")
        public Book.BookBuilder edition(final String edition) {
            this.edition = edition;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("description")
        public Book.BookBuilder description(final String description) {
            this.description = description;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("isbn")
        public Book.BookBuilder isbn(final String isbn) {
            this.isbn = isbn;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty("status")
        public Book.BookBuilder status(final String status) {
            this.status = status;
            return this;
        }

        public Book build() {
            return new Book(this.id, this.title, this.author, this.publisher, this.editionYear, this.edition, this.description, this.isbn, this.status);
        }

        @Override
        public String toString() {
            return "Book.BookBuilder(id=" + this.id + ", title=" + this.title + ", author=" + this.author + ", publisher=" + this.publisher + ", editionYear=" + this.editionYear + ", edition=" + this.edition + ", description=" + this.description + ", isbn=" + this.isbn + ", status=" + this.status + ")";
        }
    }

    public static Book.BookBuilder builder() {
        return new Book.BookBuilder();
    }

    public Integer getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public Integer getEditionYear() {
        return this.editionYear;
    }

    public String getEdition() {
        return this.edition;
    }

    public String getDescription() {
        return this.description;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public String getStatus() {
        return this.status;
    }

    @JsonProperty("id")
    public void setId(final Integer id) {
        this.id = id;
    }

    @JsonProperty("title")
    public void setTitle(final String title) {
        this.title = title;
    }

    @JsonProperty("author")
    public void setAuthor(final String author) {
        this.author = author;
    }

    @JsonProperty("publisher")
    public void setPublisher(final String publisher) {
        this.publisher = publisher;
    }

    @JsonProperty("editionYear")
    public void setEditionYear(final Integer editionYear) {
        this.editionYear = editionYear;
    }

    @JsonProperty("edition")
    public void setEdition(final String edition) {
        this.edition = edition;
    }

    @JsonProperty("description")
    public void setDescription(final String description) {
        this.description = description;
    }

    @JsonProperty("isbn")
    public void setIsbn(final String isbn) {
        this.isbn = isbn;
    }

    @JsonProperty("status")
    public void setStatus(final String status) {
        this.status = status;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Book)) return false;
        final Book other = (Book) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$editionYear = this.getEditionYear();
        final Object other$editionYear = other.getEditionYear();
        if (this$editionYear == null ? other$editionYear != null : !this$editionYear.equals(other$editionYear)) return false;
        final Object this$title = this.getTitle();
        final Object other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) return false;
        final Object this$author = this.getAuthor();
        final Object other$author = other.getAuthor();
        if (this$author == null ? other$author != null : !this$author.equals(other$author)) return false;
        final Object this$publisher = this.getPublisher();
        final Object other$publisher = other.getPublisher();
        if (this$publisher == null ? other$publisher != null : !this$publisher.equals(other$publisher)) return false;
        final Object this$edition = this.getEdition();
        final Object other$edition = other.getEdition();
        if (this$edition == null ? other$edition != null : !this$edition.equals(other$edition)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description)) return false;
        final Object this$isbn = this.getIsbn();
        final Object other$isbn = other.getIsbn();
        if (this$isbn == null ? other$isbn != null : !this$isbn.equals(other$isbn)) return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Book;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $editionYear = this.getEditionYear();
        result = result * PRIME + ($editionYear == null ? 43 : $editionYear.hashCode());
        final Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final Object $author = this.getAuthor();
        result = result * PRIME + ($author == null ? 43 : $author.hashCode());
        final Object $publisher = this.getPublisher();
        result = result * PRIME + ($publisher == null ? 43 : $publisher.hashCode());
        final Object $edition = this.getEdition();
        result = result * PRIME + ($edition == null ? 43 : $edition.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $isbn = this.getIsbn();
        result = result * PRIME + ($isbn == null ? 43 : $isbn.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Book(id=" + this.getId() + ", title=" + this.getTitle() + ", author=" + this.getAuthor() + ", publisher=" + this.getPublisher() + ", editionYear=" + this.getEditionYear() + ", edition=" + this.getEdition() + ", description=" + this.getDescription() + ", isbn=" + this.getIsbn() + ", status=" + this.getStatus() + ")";
    }

    public Book() {
    }

    public Book(final Integer id, final String title, final String author, final String publisher, final Integer editionYear, final String edition, final String description, final String isbn, final String status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.editionYear = editionYear;
        this.edition = edition;
        this.description = description;
        this.isbn = isbn;
        this.status = status;
    }
}
