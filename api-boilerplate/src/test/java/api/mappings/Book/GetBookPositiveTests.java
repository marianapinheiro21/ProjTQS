package api.mappings.Book;

import api.mappings.generic.Book;
import org.testng.annotations.Test;
import retrofit2.Response;
import java.util.List;

import static api.retrofit.Books.*;
import static api.validators.ResponseValidator.assertOk;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetBookPositiveTests {

    @Test(description = "Get all Books with success")
    public void getAllBooksWithSuccessTest() {
        Response<List<Book>> response = getAllBooks();
        assertOk(response);

        assertThat("Body should not be null", response.body(), notNullValue());
        List<Book> books = response.body();
        assertThat("List should not be empty", books, not(empty()));
    }

    @Test(description = "Get Book by ID with success")
    public void getBookByIdWithSuccessTest() {

        Book bookRequest = TestDataBuilder.createTestBook();
        Integer bookId = TestDataBuilder.createBookAndGetId(bookRequest);

        Response<Book> response = getBookById(bookId);
        assertOk(response);

        Book book = response.body();
        assertThat("Book should not be null", book, notNullValue());
        assertThat("Title should match", book.getTitle(), is(bookRequest.getTitle()));
        assertThat("Author should match", book.getAuthor(), is(bookRequest.getAuthor()));
        assertThat("ISBN should match", book.getIsbn(), is(bookRequest.getIsbn()));
        assertThat("Status should match", book.getStatus(), is(bookRequest.getStatus()));


        deleteBook(bookId);
    }
}