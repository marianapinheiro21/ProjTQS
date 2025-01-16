package api.mappings.Book;

import api.mappings.generic.Book;
import api.mappings.generic.Book.BookStatus;
import okhttp3.ResponseBody;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static api.retrofit.Books.*;
import static api.validators.ResponseValidator.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CreateBookPositiveTest {
    private List<Integer> createdBookIds;

    @BeforeMethod
    public void setup() {
        createdBookIds = new ArrayList<>();
    }

    @AfterMethod
    public void cleanup() throws IOException {
        for (Integer bookId : createdBookIds) {
            deleteBook(bookId, true);
        }
    }

    @Test(description = "Create book with minimum required fields")
    public void createBookMinimumFieldsTest() throws IOException {
        Book book = Book.builder()
                .title("Minimum Book")
                .author("Test Author")
                .isbn(generateUniqueIsbn())
                .status(BookStatus.AVAILABLE)
                .build();

        Response<Integer> response = createBook(book);
        assertCreated(response);
        validateCreatedBook(response.body(), book);
    }

    @Test(description = "Create book with all fields")
    public void createBookAllFieldsTest() throws IOException {
        Book book = Book.builder()
                .title("Complete Book")
                .author("Complete Author")
                .publisher("Test Publisher")
                .editionYear(2023)
                .edition("First Edition")
                .description("Detailed description of the book")
                .isbn(generateUniqueIsbn())
                .status(BookStatus.AVAILABLE)
                .build();

        Response<Integer> response = createBook(book);
        assertCreated(response);
        validateCreatedBook(response.body(), book);
    }

    @DataProvider(name = "validBookStatuses")
    public Object[][] validBookStatuses() {
        return new Object[][] {
                {BookStatus.AVAILABLE},
                {BookStatus.NOT_AVAILABLE},
                {BookStatus.RESERVED}
        };
    }


    @Test(description = "Create multiple books in sequence")
    public void createMultipleBooksTest() throws IOException {
        int numberOfBooks = 5;
        for (int i = 0; i < numberOfBooks; i++) {
            Book book = Book.builder()
                    .title("Book " + i)
                    .author("Author " + i)
                    .isbn(generateUniqueIsbn())
                    .status(BookStatus.AVAILABLE)
                    .build();

            Response<Integer> response = createBook(book);
            assertCreated(response);
            validateCreatedBook(response.body(), book);
        }
    }

    private void validateCreatedBook(Integer bookId, Book expectedBook) throws IOException {
        createdBookIds.add(bookId);
        Response<Book> getResponse = getBookById(bookId);
        assertOk(getResponse);

        Book createdBook = getResponse.body();
        assertThat(createdBook.getId(), is(bookId));
        assertThat(createdBook.getTitle(), is(expectedBook.getTitle()));
        assertThat(createdBook.getAuthor(), is(expectedBook.getAuthor()));
        assertThat(createdBook.getIsbn(), is(expectedBook.getIsbn()));
        assertThat(createdBook.getStatus(), is(expectedBook.getStatus()));

        if (expectedBook.getPublisher() != null) {
            assertThat(createdBook.getPublisher(), is(expectedBook.getPublisher()));
        }
        if (expectedBook.getEditionYear() != null) {
            assertThat(createdBook.getEditionYear(), is(expectedBook.getEditionYear()));
        }
        if (expectedBook.getEdition() != null) {
            assertThat(createdBook.getEdition(), is(expectedBook.getEdition()));
        }
        if (expectedBook.getDescription() != null) {
            assertThat(createdBook.getDescription(), is(expectedBook.getDescription()));
        }
    }

    private String generateUniqueIsbn() {
        return "978" + System.currentTimeMillis() % 10000000000L;
    }
}