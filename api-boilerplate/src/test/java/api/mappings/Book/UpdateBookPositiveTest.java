package api.mappings.Book;

import api.mappings.generic.Book;
import api.mappings.generic.BookStatus;
import okhttp3.ResponseBody;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;

import static api.retrofit.Books.*;
import static api.validators.ResponseValidator.assertOk;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class UpdateBookPositiveTest {
    private Integer bookId;
    private Book originalBook;

    @BeforeMethod
    public void setup() throws IOException {
        originalBook = Book.builder()
                .title("Original Title")
                .author("Original Author")
                .publisher("Original Publisher")
                .editionYear(2020)
                .edition("First Edition")
                .description("Original Description")
                .isbn(generateUniqueIsbn())
                .status(BookStatus.AVAILABLE.toString())
                .build();

        Response<ResponseBody> response = createBook(originalBook);
        bookId = Integer.parseInt(response.body().string());
    }

    @AfterMethod
    public void cleanup() {
        if (bookId != null) {
            deleteBook(bookId);
        }
    }

    @Test(description = "Update all book fields")
    public void updateAllBookFieldsTest() throws IOException {
        Book updateRequest = Book.builder()
                .title("Updated Title")
                .author("Updated Author")
                .publisher("Updated Publisher")
                .editionYear(2021)
                .edition("Second Edition")
                .description("Updated Description")
                .status(BookStatus.NOT_AVAILABLE.toString())
                .build();

        Response<Book> response = updateBook(bookId, updateRequest);
        assertOk(response);

        Book updatedBook = response.body();
        assertThat("Updated book should not be null", updatedBook, notNullValue());
        assertThat("Title should be updated", updatedBook.getTitle(), is(updateRequest.getTitle()));
        assertThat("Author should be updated", updatedBook.getAuthor(), is(updateRequest.getAuthor()));
        assertThat("Publisher should be updated", updatedBook.getPublisher(), is(updateRequest.getPublisher()));
        assertThat("Edition year should be updated", updatedBook.getEditionYear(), is(updateRequest.getEditionYear()));
        assertThat("Edition should be updated", updatedBook.getEdition(), is(updateRequest.getEdition()));
        assertThat("Description should be updated", updatedBook.getDescription(), is(updateRequest.getDescription()));
        assertThat("Status should be updated", updatedBook.getStatus(), is(updateRequest.getStatus()));
    }

    @Test(description = "Update only book title")
    public void updateBookTitleOnlyTest() throws IOException {
        Book updateRequest = Book.builder()
                .title("New Title Only")
                .build();

        Response<Book> response = updateBook(bookId, updateRequest);
        assertOk(response);

        Book updatedBook = response.body();
        assertThat("Title should be updated", updatedBook.getTitle(), is(updateRequest.getTitle()));
        assertThat("Author should remain unchanged", updatedBook.getAuthor(), is(originalBook.getAuthor()));
    }

    @Test(description = "Update book status")
    public void updateBookStatusTest() throws IOException {
        Book updateRequest = Book.builder()
                .status(BookStatus.RESERVED.toString())
                .build();

        Response<Book> response = updateBook(bookId, updateRequest);
        assertOk(response);

        Book updatedBook = response.body();
        assertThat("Status should be updated", updatedBook.getStatus(), is(updateRequest.getStatus()));
    }

    @Test(description = "Update book description with special characters")
    public void updateBookDescriptionWithSpecialCharsTest() throws IOException {
        String specialDescription = "Special chars: áéíóú ñ @ # $ % &";
        Book updateRequest = Book.builder()
                .description(specialDescription)
                .build();

        Response<Book> response = updateBook(bookId, updateRequest);
        assertOk(response);

        Book updatedBook = response.body();
        assertThat("Description should be updated with special chars",
                updatedBook.getDescription(), is(specialDescription));
    }

    @Test(description = "Update book with maximum length fields")
    public void updateBookWithMaxLengthFieldsTest() throws IOException {
        String maxTitle = "T".repeat(255);
        String maxDescription = "D".repeat(1000);

        Book updateRequest = Book.builder()
                .title(maxTitle)
                .description(maxDescription)
                .build();

        Response<Book> response = updateBook(bookId, updateRequest);
        assertOk(response);

        Book updatedBook = response.body();
        assertThat("Title should be updated with max length", updatedBook.getTitle(), is(maxTitle));
        assertThat("Description should be updated with max length",
                updatedBook.getDescription(), is(maxDescription));
    }

    private String generateUniqueIsbn() {
        return "978" + System.currentTimeMillis() % 10000000000L;
    }
}