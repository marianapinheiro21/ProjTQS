package api.mappings.Book;

import api.mappings.generic.Book;
import api.mappings.generic.BookStatus;
import okhttp3.ResponseBody;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;


import static api.retrofit.Books.*;
import static api.validators.ResponseValidator.assertCreated;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CreateBookPositiveTests {

    private Integer bookId;

    @AfterMethod
    public void cleanup() {
        if (bookId != null) {
            deleteBook(bookId);
        }
    }

    @Test(description = "Create book with all fields")
    public void createCompleteBookTest() throws IOException {
        Book bookRequest = Book.builder()
                .title("Os marmajos da noite")
                .author("Olivier Caricas")
                .isbn(generateUniqueIsbn())
                .publisher("Papiros do bem")
                .description("Cenas para ler")
                .editionYear(2020)
                .status(BookStatus.AVAILABLE.toString())
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertCreated(response);

        bookId = Integer.parseInt(response.body().string());
        Book bookResponse = getBookById(bookId).body();

        assertThat("Book response should not be null", bookResponse, notNullValue());
        assertThat("Id should not be null", bookResponse.getId(), notNullValue());
        assertThat("Title should match", bookResponse.getTitle(), is(bookRequest.getTitle()));
        assertThat("Author should match", bookResponse.getAuthor(), is(bookRequest.getAuthor()));
        assertThat("ISBN should match", bookResponse.getIsbn(), is(bookRequest.getIsbn()));
        assertThat("Publisher should match", bookResponse.getPublisher(), is(bookRequest.getPublisher()));
        assertThat("Description should match", bookResponse.getDescription(), is(bookRequest.getDescription()));
        assertThat("Edition year should match", bookResponse.getEditionYear(), is(bookRequest.getEditionYear()));
        assertThat("Status should match", bookResponse.getStatus(), is(bookRequest.getStatus()));
    }

    @Test(description = "Create book with only required fields")
    public void createBookWithRequiredFieldsOnlyTest() throws IOException {
        Book bookRequest = Book.builder()
                .title("Lamparinas")
                .author("Jolman")
                .isbn(generateUniqueIsbn())
                .status(BookStatus.AVAILABLE.toString())
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertCreated(response);

        bookId = Integer.parseInt(response.body().string());
        Book bookResponse = getBookById(bookId).body();

        assertThat("Book response should not be null", bookResponse, notNullValue());
        assertThat("Title should match", bookResponse.getTitle(), is(bookRequest.getTitle()));
        assertThat("Author should match", bookResponse.getAuthor(), is(bookRequest.getAuthor()));
        assertThat("ISBN should match", bookResponse.getIsbn(), is(bookRequest.getIsbn()));
        assertThat("Status should match", bookResponse.getStatus(), is(bookRequest.getStatus()));
    }

    @Test(description = "Create book with minimum valid ISBN")
    public void createBookWithMinimumValidIsbnTest() throws IOException {
        String validIsbn = generateUniqueIsbn();
        Book bookRequest = createDefaultTestBook();
        bookRequest.setIsbn(validIsbn);

        Response<ResponseBody> response = createBook(bookRequest);
        assertCreated(response);

        bookId = Integer.parseInt(response.body().string());
        Book bookResponse = getBookById(bookId).body();

        assertThat("ISBN should match", bookResponse.getIsbn(), is(validIsbn));
    }
}