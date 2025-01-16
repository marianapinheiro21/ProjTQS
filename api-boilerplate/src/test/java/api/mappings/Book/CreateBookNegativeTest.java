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

public class CreateBookNegativeTest {

    @Test(description = "Create book with null title")
    @SneakyThrows
    public void createBookNullTitleTest() {
        Book book = Book.builder()
                .author("Test Author")
                .isbn(generateUniqueIsbn())
                .status(BookStatus.AVAILABLE)
                .build();

        Response<Integer> response = createBook(book);
        assertBadRequest(response);
    }

    @Test(description = "Create book with empty required fields")
    @SneakyThrows
    public void createBookEmptyFieldsTest() {
        Book book = Book.builder()
                .title("")
                .author("")
                .isbn("")
                .status(BookStatus.AVAILABLE)
                .build();

        Response<Integer> response = createBook(book);
        assertBadRequest(response);
    }

    @DataProvider(name = "invalidIsbnData")
    public Object[][] invalidIsbnData() {
        return new Object[][] {
                {"123"}, // muito curto
                {"9780123456789"}, // formato inválido
                {"abc123456789"}, // caracteres não numéricos
                {"9999999999999"}, // número inválido
                {"978-0123456789"}, // com hífen
        };
    }

    @Test(description = "Create book with invalid ISBN", dataProvider = "invalidIsbnData")
    @SneakyThrows
    public void createBookInvalidIsbnTest(String isbn) {
        Book book = Book.builder()
                .title("Test Book")
                .author("Test Author")
                .isbn(isbn)
                .status(BookStatus.AVAILABLE)
                .build();

        Response<Integer> response = createBook(book);
        assertBadRequest(response);
    }

    @Test(description = "Create book with duplicate ISBN")
    @SneakyThrows
    public void createBookDuplicateIsbnTest() throws IOException {
        String isbn = generateUniqueIsbn();

        // Criar primeiro livro
        Book book1 = Book.builder()
                .title("First Book")
                .author("Test Author")
                .isbn(isbn)
                .status(BookStatus.AVAILABLE)
                .build();

        Response<Integer> response1 = createBook(book1);
        assertCreated(response1);
        Integer bookId = response1.body();

        // Tentar criar segundo livro com mesmo ISBN
        Book book2 = Book.builder()
                .title("Second Book")
                .author("Another Author")
                .isbn(isbn)
                .status(BookStatus.AVAILABLE)
                .build();

        Response<Integer> response2 = createBook(book2);
        assertBadRequest(response2);


        deleteBook(bookId, true);
    }

    @Test(description = "Create book with invalid year")
    @SneakyThrows
    public void createBookInvalidYearTest() {
        Book book = Book.builder()
                .title("Test Book")
                .author("Test Author")
                .isbn(generateUniqueIsbn())
                .editionYear(2025) // ano futuro
                .status(BookStatus.AVAILABLE)
                .build();

        Response<Integer> response = createBook(book);
        assertBadRequest(response);
    }

    @Test(description = "Create book with extremely long fields")
    @SneakyThrows
    public void createBookExtremelyLongFieldsTest() {
        String veryLongString = "a".repeat(1000);
        Book book = Book.builder()
                .title(veryLongString)
                .author(veryLongString)
                .publisher(veryLongString)
                .description(veryLongString)
                .isbn(generateUniqueIsbn())
                .status(BookStatus.AVAILABLE)
                .build();

        Response<Integer> response = createBook(book);
        assertBadRequest(response);
    }

    @Test(description = "Create book with invalid status")
    @SneakyThrows
    public void createBookInvalidStatusTest() {
        Book book = Book.builder()
                .title("Test Book")
                .author("Test Author")
                .isbn(generateUniqueIsbn())
                .status(null)
                .build();

        Response<Integer> response = createBook(book);
        assertBadRequest(response);
    }



    private String generateUniqueIsbn() {
        return "978" + System.currentTimeMillis() % 10000000000L;
    }
}