package api.mappings.Book;

import api.mappings.generic.Book;
import api.mappings.generic.BookStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

import static api.retrofit.Books.*;
import static api.validators.ResponseValidator.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DeleteBookPositiveTests {

    private Integer bookId;
    private Book testBook;

    @BeforeMethod
    public void setup() throws IOException {
        testBook = createDefaultTestBook();
        bookId = createBookAndGetId(testBook);
    }

    @Test(description = "Delete available book successfully")
    public void deleteAvailableBookTest() throws IOException {

        Response<ResponseBody> deleteResponse = deleteBook(bookId);
        assertNoContent(deleteResponse);


        Response<Book> getResponse = getBookById(bookId);
        assertNotFound(getResponse);
    }

    @Test(description = "Delete book and verify it's not in the list")
    public void deleteBookAndVerifyListTest() throws IOException {

        Response<List<Book>> initialListResponse = getAllBooks();
        List<Book> initialBooks = initialListResponse.body();


        Response<ResponseBody> deleteResponse = deleteBook(bookId);
        assertNoContent(deleteResponse);


        Response<List<Book>> updatedListResponse = getAllBooks();
        List<Book> updatedBooks = updatedListResponse.body();


        assertThat(updatedBooks.size(), is(initialBooks.size() - 1));
        assertThat(updatedBooks.stream()
                        .map(Book::getId)
                        .collect(Collectors.toList()),
                not(hasItem(bookId)));
    }

    @Test(description = "Delete book with NOT_AVAILABLE status")
    public void deleteNotAvailableBookTest() throws IOException {
        // Update book status to NOT_AVAILABLE
        Book updateRequest = Book.builder()
                .status(BookStatus.NOT_AVAILABLE.toString())
                .build();
        updateBook(bookId, updateRequest);


        Response<ResponseBody> deleteResponse = deleteBook(bookId);
        assertNoContent(deleteResponse);


        Response<Book> getResponse = getBookById(bookId);
        assertNotFound(getResponse);
    }
}