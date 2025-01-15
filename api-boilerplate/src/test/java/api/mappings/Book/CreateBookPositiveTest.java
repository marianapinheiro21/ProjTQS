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

public class CreateBookPositiveTest {
    Integer bookId;

    @AfterMethod
    public void cleanUp() {
        if (bookId != null) {
            deleteBook(bookId);
        }
    }

    @Test(description = "Create new book with all fields")
    public void createCompleteBookTest() throws IOException {
        Book bookRequest = Book.builder()
                .title("The Lord of the Rings")
                .author("J.R.R. Tolkien")
                .publisher("Allen & Unwin")
                .editionYear(1954)
                .edition("First Edition")
                .description("Epic high-fantasy novel")
                .isbn("9780544003415")
                .status(BookStatus.AVAILABLE.toString())
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertCreated(response);

        assertThat("Body is not null", response.body(), notNullValue());
        bookId = Integer.parseInt(response.body().string());
        Book bookResponse = getBookById(bookId).body();

        assertFullBookDetails(bookResponse, bookRequest);
    }

    @Test(description = "Create book with minimum required fields")
    public void createMinimalBookTest() throws IOException {
        Book bookRequest = Book.builder()
                .title("Minimal Book")
                .author("Test Author")
                .isbn("9780544003416")
                .status(BookStatus.AVAILABLE.toString())
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertCreated(response);
        verifyBasicBookDetails(response, bookRequest);
    }

    @Test(description = "Create book with special characters in title and description")
    public void createBookWithSpecialCharactersTest() throws IOException {
        Book bookRequest = Book.builder()
                .title("O Código Da Vinci: Special Edition!")
                .author("Dan Brown")
                .description("Mystery & Suspense: A thrilling journey through Paris's Louvre Museum!")
                .isbn("9780545010225")
                .status(BookStatus.AVAILABLE.toString())
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertCreated(response);
        verifyBasicBookDetails(response, bookRequest);
    }

    @Test(description = "Create book with maximum length fields")
    public void createBookWithMaxLengthFieldsTest() throws IOException {
        String maxTitle = "T".repeat(255);
        String maxDescription = "D".repeat(1000);

        Book bookRequest = Book.builder()
                .title(maxTitle)
                .author("Test Author")
                .description(maxDescription)
                .isbn("9780544003417")
                .status(BookStatus.AVAILABLE.toString())
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertCreated(response);
        verifyBasicBookDetails(response, bookRequest);
    }

    @Test(description = "Create book with all status types")
    public void createBookWithDifferentStatusTest() throws IOException {
        for (BookStatus status : BookStatus.values()) {
            Book bookRequest = Book.builder()
                    .title("Status Test Book")
                    .author("Test Author")
                    .isbn(generateUniqueIsbn())
                    .status(status.toString())
                    .build();

            Response<ResponseBody> response = createBook(bookRequest);
            assertCreated(response);
            verifyBasicBookDetails(response, bookRequest);

            // Cleanup for this iteration
            deleteBook(bookId);
        }
    }


    @Test(description = "Create book with oldest possible edition year")
    public void createBookWithOldestYearTest() throws IOException {
        Book bookRequest = Book.builder()
                .title("Ancient Book")
                .author("Ancient Author")
                .editionYear(1450) // Assumindo que 1450 é o ano mais antigo permitido
                .isbn("9780544003419")
                .status(BookStatus.AVAILABLE.toString())
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertCreated(response);
        verifyBasicBookDetails(response, bookRequest);
    }

    @Test(description = "Create book with current year edition")
    public void createBookWithCurrentYearTest() throws IOException {
        int currentYear = java.time.Year.now().getValue();
        Book bookRequest = Book.builder()
                .title("Modern Book")
                .author("Modern Author")
                .editionYear(currentYear)
                .isbn("9780544003420")
                .status(BookStatus.AVAILABLE.toString())
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertCreated(response);
        verifyBasicBookDetails(response, bookRequest);
    }

    @Test(description = "Create multiple books in sequence")
    public void createMultipleBooksTest() throws IOException {
        for (int i = 1; i <= 5; i++) {
            Book bookRequest = Book.builder()
                    .title("Book " + i)
                    .author("Author " + i)
                    .isbn(generateUniqueIsbn())
                    .status(BookStatus.AVAILABLE.toString())
                    .build();

            Response<ResponseBody> response = createBook(bookRequest);
            assertCreated(response);
            verifyBasicBookDetails(response, bookRequest);

            // Cleanup for this iteration
            deleteBook(bookId);
        }
    }

    // Helper methods
    private void assertFullBookDetails(Book bookResponse, Book bookRequest) {
        assertThat("Book response should not be null", bookResponse, notNullValue());
        assertThat("id should not be null", bookResponse.getId(), notNullValue());
        assertThat("Title should match", bookResponse.getTitle(), is(bookRequest.getTitle()));
        assertThat("Author should match", bookResponse.getAuthor(), is(bookRequest.getAuthor()));
        assertThat("Publisher should match", bookResponse.getPublisher(), is(bookRequest.getPublisher()));
        assertThat("Edition year should match", bookResponse.getEditionYear(), is(bookRequest.getEditionYear()));
        assertThat("Edition should match", bookResponse.getEdition(), is(bookRequest.getEdition()));
        assertThat("Description should match", bookResponse.getDescription(), is(bookRequest.getDescription()));
        assertThat("ISBN should match", bookResponse.getIsbn(), is(bookRequest.getIsbn()));
        assertThat("Status should match", bookResponse.getStatus(), is(bookRequest.getStatus()));
    }

    private void verifyBasicBookDetails(Response<ResponseBody> response, Book bookRequest) throws IOException {
        assertThat("Body is not null", response.body(), notNullValue());
        bookId = Integer.parseInt(response.body().string());
        Book bookResponse = getBookById(bookId).body();

        assertThat("Book response should not be null", bookResponse, notNullValue());
        assertThat("id should not be null", bookResponse.getId(), notNullValue());
        assertThat("Title should match", bookResponse.getTitle(), is(bookRequest.getTitle()));
        assertThat("Author should match", bookResponse.getAuthor(), is(bookRequest.getAuthor()));
        assertThat("ISBN should match", bookResponse.getIsbn(), is(bookRequest.getIsbn()));
        assertThat("Status should match", bookResponse.getStatus(), is(bookRequest.getStatus()));
    }

    private String generateUniqueIsbn() {
        return "978" + System.currentTimeMillis() % 10000000000L;
    }
}