package api.mappings.Book;

import api.mappings.generic.Book;
import api.mappings.generic.Book.BookStatus;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import retrofit2.Response;

import static api.retrofit.Books.*;
import static api.validators.ResponseValidator.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DeleteBookNegativeTest {

    @Test(description = "Delete non-existent book")
    @SneakyThrows
    public void deleteNonExistentBookTest() {
        Response<ResponseBody> response = deleteBook(99999, false);
        assertNotFound(response);
    }

    @DataProvider(name = "invalidIds")
    public Object[][] invalidIds() {
        return new Object[][] {
                {-1},
                {0},
                {Integer.MIN_VALUE}
        };
    }

    @Test(dataProvider = "invalidIds", description = "Delete book with invalid ID")
    @SneakyThrows
    public void deleteBookInvalidIdTest(Integer invalidId) {
        Response<ResponseBody> response = deleteBook(invalidId, false);
        assertBadRequest(response);
    }

    @Test(description = "Delete reserved book without force")
    @SneakyThrows
    public void deleteReservedBookWithoutForceTest() throws IOException {

        Book reservedBook = createBookWithStatus(BookStatus.RESERVED);


        Response<ResponseBody> response = deleteBook(reservedBook.getId(), false);
        assertThat(response.code(), is(409));


        deleteBook(reservedBook.getId(), true);
    }

    @Test(description = "Delete book twice")
    @SneakyThrows
    public void deleteBookTwiceTest() throws IOException {

        Book book = createTestBook();


        Response<ResponseBody> firstDelete = deleteBook(book.getId(), false);
        assertNoContent(firstDelete);


        Response<ResponseBody> secondDelete = deleteBook(book.getId(), false);
        assertNotFound(secondDelete);
    }

    @Test(description = "Delete book with invalid status transition")
    @SneakyThrows
    public void deleteBookInvalidStatusTransitionTest() throws IOException {

        Book book = createBookWithStatus(BookStatus.NOT_AVAILABLE);


        Book updatedBook = Book.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .status(BookStatus.RESERVED)
                .build();
        updateBook(book.getId(), updatedBook);

        Response<ResponseBody> response = deleteBook(book.getId(), false);
        assertThat(response.code(), is(409));


        deleteBook(book.getId(), true);
    }

    @Test(description = "Delete book with concurrent modifications")
    @SneakyThrows
    public void deleteBookConcurrentModificationTest() throws IOException {
        Book book = createTestBook();


        Book updatedBook = Book.builder()
                .id(book.getId())
                .title("Updated Title")
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .status(BookStatus.NOT_AVAILABLE)
                .build();
        updateBook(book.getId(), updatedBook);


        Response<ResponseBody> response = deleteBook(book.getId(), false);
        assertThat(response.code(), is(409));


        deleteBook(book.getId(), true);
    }



    @Test(description = "Delete book with invalid force parameter")
    @SneakyThrows
    public void deleteBookInvalidForceParameterTest() throws IOException {
        Book book = createTestBook();
        Response<ResponseBody> response = deleteBookWithInvalidForce(book.getId(), "invalid");
        assertBadRequest(response);


        deleteBook(book.getId(), true);
    }



    private Book createTestBook() throws IOException {
        Book book = Book.builder()
                .title("Test Book")
                .author("Test Author")
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