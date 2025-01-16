package api.mappings.Book;

import api.mappings.generic.Book;
import api.mappings.generic.Book.BookStatus;
import lombok.SneakyThrows;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import retrofit2.Response;

import static api.retrofit.Books.*;
import static api.validators.ResponseValidator.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UpdateBookNegativeTest {

    @Test(description = "Update non-existent book")
    @SneakyThrows
    public void updateNonExistentBookTest() {
        Book book = Book.builder()
                .id(99999)
                .title("Test Book")
                .author("Test Author")
                .isbn(generateUniqueIsbn())
                .status(BookStatus.AVAILABLE)
                .build();

        Response<Integer> response = updateBook(99999, book);
        assertNotFound(response);
    }

    @Test(description = "Update book with null title")
    @SneakyThrows
    public void updateBookNullTitleTest() throws IOException {

        Book originalBook = createValidBook();

        Book updateBook = Book.builder()
                .id(originalBook.getId())
                .title(null)
                .author("Test Author")
                .isbn(originalBook.getIsbn())
                .status(BookStatus.AVAILABLE)
                .build();

        Response<Integer> response = updateBook(originalBook.getId(), updateBook);
        assertBadRequest(response);

        // Cleanup
        deleteBook(originalBook.getId(), true);
    }

    @Test(description = "Update book with empty required fields")
    @SneakyThrows
    public void updateBookEmptyFieldsTest() throws IOException {
        Book originalBook = createValidBook();

        Book updateBook = Book.builder()
                .id(originalBook.getId())
                .title("")
                .author("")
                .isbn(originalBook.getIsbn())
                .status(BookStatus.AVAILABLE)
                .build();

        Response<Integer> response = updateBook(originalBook.getId(), updateBook);
        assertBadRequest(response);

        deleteBook(originalBook.getId(), true);
    }

    @Test(description = "Update book with mismatched IDs")
    @SneakyThrows
    public void updateBookMismatchedIdsTest() throws IOException {
        Book originalBook = createValidBook();

        Book updateBook = Book.builder()
                .id(originalBook.getId() + 1)
                .title("Test Book")
                .author("Test Author")
                .isbn(originalBook.getIsbn())
                .status(BookStatus.AVAILABLE)
                .build();

        Response<Integer> response = updateBook(originalBook.getId(), updateBook);
        assertBadRequest(response);

        deleteBook(originalBook.getId(), true);
    }

    @Test(description = "Update book with different ISBN")
    @SneakyThrows
    public void updateBookDifferentIsbnTest() throws IOException {
        Book originalBook = createValidBook();

        Book updateBook = Book.builder()
                .id(originalBook.getId())
                .title("Test Book")
                .author("Test Author")
                .isbn(generateUniqueIsbn())
                .status(BookStatus.AVAILABLE)
                .build();

        Response<Integer> response = updateBook(originalBook.getId(), updateBook);
        assertBadRequest(response);

        deleteBook(originalBook.getId(), true);
    }

    @Test(description = "Update book with invalid year")
    @SneakyThrows
    public void updateBookInvalidYearTest() throws IOException {
        Book originalBook = createValidBook();

        Book updateBook = Book.builder()
                .id(originalBook.getId())
                .title("Test Book")
                .author("Test Author")
                .isbn(originalBook.getIsbn())
                .editionYear(2025)
                .status(BookStatus.AVAILABLE)
                .build();

        Response<Integer> response = updateBook(originalBook.getId(), updateBook);
        assertBadRequest(response);

        deleteBook(originalBook.getId(), true);
    }

    @Test(description = "Update book with extremely long fields")
    @SneakyThrows
    public void updateBookExtremelyLongFieldsTest() throws IOException {
        Book originalBook = createValidBook();

        String veryLongString = "a".repeat(1000);
        Book updateBook = Book.builder()
                .id(originalBook.getId())
                .title(veryLongString)
                .author(veryLongString)
                .publisher(veryLongString)
                .description(veryLongString)
                .isbn(originalBook.getIsbn())
                .status(BookStatus.AVAILABLE)
                .build();

        Response<Integer> response = updateBook(originalBook.getId(), updateBook);
        assertBadRequest(response);

        deleteBook(originalBook.getId(), true);
    }

    @Test(description = "Update book with invalid status transition")
    @SneakyThrows
    public void updateBookInvalidStatusTransitionTest() throws IOException {
        // Criar livro com status RESERVED
        Book originalBook = createBookWithStatus(BookStatus.RESERVED);

        // Tentar mudar diretamente para NOT_AVAILABLE
        Book updateBook = Book.builder()
                .id(originalBook.getId())
                .title(originalBook.getTitle())
                .author(originalBook.getAuthor())
                .isbn(originalBook.getIsbn())
                .status(BookStatus.NOT_AVAILABLE)
                .build();

        Response<Integer> response = updateBook(originalBook.getId(), updateBook);
        assertBadRequest(response);

        deleteBook(originalBook.getId(), true);
    }



    private Book createValidBook() throws IOException {
        Book book = Book.builder()
                .title("Original Book")
                .author("Original Author")
                .isbn(generateUniqueIsbn())
                .status(BookStatus.AVAILABLE)
                .build();

        Response<Integer> response = createBook(book);
        assertCreated(response);
        Integer id = response.body();
        book.setId(id);
        return book;
    }

    private Book createBookWithStatus(BookStatus status) throws IOException {
        Book book = Book.builder()
                .title("Status Test Book")
                .author("Test Author")
                .isbn(generateUniqueIsbn())
                .status(status)
                .build();

        Response<Integer> response = createBook(book);
        assertCreated(response);
        Integer id = response.body();
        book.setId(id);
        return book;
    }

    private String generateUniqueIsbn() {
        return "978" + System.currentTimeMillis() % 10000000000L;
    }
}