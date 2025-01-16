package api.mappings.Book;

<<<<<<< HEAD
import api.mappings.generic.Book;
import api.mappings.generic.Book.BookStatus;
import okhttp3.ResponseBody;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
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

public class UpdateBookPositiveTest {
    private List<Integer> bookIds;
    private Book originalBook;

    @BeforeMethod
    public void setup() throws IOException {
        bookIds = new ArrayList<>();
        originalBook = createTestBook();
    }

    @AfterMethod
    public void cleanup() throws IOException {
        for (Integer id : bookIds) {
            deleteBook(id, true);
        }
    }

    @Test(description = "Update all fields of a book")
    public void updateAllFieldsTest() throws IOException {
        Book updatedBook = Book.builder()
                .id(originalBook.getId())
                .title("Updated Title")
                .author("Updated Author")
                .publisher("Updated Publisher")
                .editionYear(2023)
                .edition("2nd Edition")
                .description("Updated Description")
                .isbn(originalBook.getIsbn())
                .status(BookStatus.NOT_AVAILABLE)
                .build();

        Response<Integer> updateResponse = updateBook(originalBook.getId(), updatedBook);
        assertOk(updateResponse);

        validateUpdatedBook(updatedBook);
    }

    @Test(description = "Update only required fields")
    public void updateRequiredFieldsTest() throws IOException {
        Book updatedBook = Book.builder()
                .id(originalBook.getId())
                .title("Updated Title")
                .author("Updated Author")
                .isbn(originalBook.getIsbn())
                .status(BookStatus.AVAILABLE)
                .build();

        Response<Integer> updateResponse = updateBook(originalBook.getId(), updatedBook);
        assertOk(updateResponse);

        validateUpdatedBook(updatedBook);
    }

    @DataProvider(name = "statusTransitions")
    public Object[][] statusTransitions() {
        return new Object[][] {
                {BookStatus.AVAILABLE, BookStatus.NOT_AVAILABLE},
                {BookStatus.NOT_AVAILABLE, BookStatus.AVAILABLE},
                {BookStatus.AVAILABLE, BookStatus.RESERVED},
                {BookStatus.RESERVED, BookStatus.AVAILABLE}
        };
    }

    @Test(description = "Update book status", dataProvider = "statusTransitions")
    public void updateBookStatusTest(BookStatus initialStatus, BookStatus newStatus) throws IOException {
        // Criar livro com status inicial
        Book statusBook = createBookWithStatus(initialStatus);

        // Atualizar apenas o status
        Book updatedBook = Book.builder()
                .id(statusBook.getId())
                .title(statusBook.getTitle())
                .author(statusBook.getAuthor())
                .isbn(statusBook.getIsbn())
                .status(newStatus)
                .build();

        Response<Integer> updateResponse = updateBook(statusBook.getId(), updatedBook);
        assertOk(updateResponse);

        validateUpdatedBook(updatedBook);
    }

    @Test(description = "Update book with special characters")
    public void updateBookSpecialCharsTest() throws IOException {
        Book updatedBook = Book.builder()
                .id(originalBook.getId())
                .title("áéíóú")
                .author(" @#$%")
                .publisher("publisher & co.")
                .description("d ")
                .isbn(originalBook.getIsbn())
                .status(BookStatus.AVAILABLE)
                .build();

        Response<Integer> updateResponse = updateBook(originalBook.getId(), updatedBook);
        assertOk(updateResponse);

        validateUpdatedBook(updatedBook);
    }

    @Test(description = "Update book with maximum length fields")
    public void updateBookMaxLengthTest() throws IOException {
        String maxLength = "a".repeat(255); // Assumindo max length de 255
        Book updatedBook = Book.builder()
                .id(originalBook.getId())
                .title(maxLength)
                .author(maxLength)
                .publisher(maxLength)
                .description(maxLength)
                .isbn(originalBook.getIsbn())
                .status(BookStatus.AVAILABLE)
                .build();

        Response<Integer> updateResponse = updateBook(originalBook.getId(), updatedBook);
        assertOk(updateResponse);

        validateUpdatedBook(updatedBook);
    }

    @Test(description = "Update book multiple times")
    public void updateBookMultipleTimesTest() throws IOException {
        for (int i = 1; i <= 3; i++) {
            Book updatedBook = Book.builder()
                    .id(originalBook.getId())
                    .title("Title Version " + i)
                    .author("Author Version " + i)
                    .publisher("Publisher Version " + i)
                    .isbn(originalBook.getIsbn())
                    .status(BookStatus.AVAILABLE)
                    .build();

            Response<Integer> updateResponse = updateBook(originalBook.getId(), updatedBook);
            assertOk(updateResponse);

            validateUpdatedBook(updatedBook);
        }
    }

    // Métodos auxiliares

    private Book createTestBook() throws IOException {
        Book book = Book.builder()
                .title("Original Book")
                .author("Original Author")
                .publisher("Original Publisher")
                .editionYear(2022)
                .edition("1st Edition")
                .description("Original Description")
                .isbn(generateUniqueIsbn())
                .status(BookStatus.AVAILABLE)
                .build();

        Response<Integer> response = createBook(book);
        assertCreated(response);
        Integer id = response.body();
        bookIds.add(id);
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
        bookIds.add(id);
        book.setId(id);
        return book;
    }

    private void validateUpdatedBook(Book expectedBook) throws IOException {
        Response<Book> getResponse = getBookById(expectedBook.getId());
        assertOk(getResponse);

        Book actualBook = getResponse.body();
        assertBookEquals(actualBook, expectedBook);
    }

    private void assertBookEquals(Book actual, Book expected) {
        assertThat(actual.getId(), is(expected.getId()));
        assertThat(actual.getTitle(), is(expected.getTitle()));
        assertThat(actual.getAuthor(), is(expected.getAuthor()));
        assertThat(actual.getIsbn(), is(expected.getIsbn()));
        assertThat(actual.getStatus(), is(expected.getStatus()));

        if (expected.getPublisher() != null) {
            assertThat(actual.getPublisher(), is(expected.getPublisher()));
        }
        if (expected.getEditionYear() != null) {
            assertThat(actual.getEditionYear(), is(expected.getEditionYear()));
        }
        if (expected.getEdition() != null) {
            assertThat(actual.getEdition(), is(expected.getEdition()));
        }
        if (expected.getDescription() != null) {
            assertThat(actual.getDescription(), is(expected.getDescription()));
        }
    }

    private String generateUniqueIsbn() {
        return "978" + System.currentTimeMillis() % 10000000000L;
    }
}
=======
public class UpdateBookPositiveTest {
}
>>>>>>> a6937f70dacf73c82f07b1adafa0bbc624692761
