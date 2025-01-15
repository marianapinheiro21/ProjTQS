package api.mappings.Book;

import api.mappings.generic.Book;
import api.mappings.generic.BookStatus;
import okhttp3.ResponseBody;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static api.retrofit.Books.*;
import static api.validators.ResponseValidator.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DeleteBookPositiveTest {
    private List<Integer> bookIds;

    @BeforeMethod
    public void setup() {
        bookIds = new ArrayList<>();
    }

    @Test(description = "Delete available book")
    public void deleteAvailableBookTest() throws IOException {
        Book book = Book.builder()
                .title("Book to Delete")
                .author("Test Author")
                .isbn(generateUniqueIsbn())
                .status(BookStatus.AVAILABLE.toString())
                .build();

        Response<ResponseBody> createResponse = createBook(book);
        Integer bookId = Integer.parseInt(createResponse.body().string());

        Response<ResponseBody> deleteResponse = deleteBook(bookId);
        assertNoContent(deleteResponse);

        Response<Book> getResponse = getBookById(bookId);
        assertNotFound(getResponse);
    }

    @Test(description = "Delete multiple books")
    public void deleteMultipleBooksTest() throws IOException {
        // Create multiple books
        for (int i = 0; i < 3; i++) {
            Book book = Book.builder()
                    .title("Book " + i)
                    .author("Test Author")
                    .isbn(generateUniqueIsbn())
                    .status(BookStatus.AVAILABLE.toString())
                    .build();

            Response<ResponseBody> createResponse = createBook(book);
            bookIds.add(Integer.parseInt(createResponse.body().string()));
        }

        // Delete all created books
        for (Integer id : bookIds) {
            Response<ResponseBody> deleteResponse = deleteBook(id);
            assertNoContent(deleteResponse);

            Response<Book> getResponse = getBookById(id);
            assertNotFound(getResponse);
        }
    }

    @Test(description = "Delete book and verify it's removed from search results")
    public void deleteBookAndVerifySearchTest() throws IOException {
        String uniqueTitle = "Unique Title " + System.currentTimeMillis();
        Book book = Book.builder()
                .title(uniqueTitle)
                .author("Test Author")
                .isbn(generateUniqueIsbn())
                .status(BookStatus.AVAILABLE.toString())
                .build();

        Response<ResponseBody> createResponse = createBook(book);
        Integer bookId = Integer.parseInt(createResponse.body().string());

        // Delete the book
        Response<ResponseBody> deleteResponse = deleteBook(bookId);
        assertNoContent(deleteResponse);

        // Verify book doesn't appear in search results
        Response<List<Book>> searchResponse = searchBooksByTitle(uniqueTitle);
        assertOk(searchResponse);
        List<Book> searchResults = searchResponse.body();
        assertThat("Search results should not contain deleted book",
                searchResults.stream().noneMatch(b -> b.getId().equals(bookId)));
    }

    @Test(description = "Delete book and verify all associated data is removed")
    public void deleteBookAndVerifyAssociatedDataTest() throws IOException {
        Book book = Book.builder()
                .title("Complete Book")
                .author("Test Author")
                .publisher("Test Publisher")
                .editionYear(2020)
                .edition("First Edition")
                .description("Test Description")
                .isbn(generateUniqueIsbn())
                .status(BookStatus.AVAILABLE.toString())
                .build();

        Response<ResponseBody> createResponse = createBook(book);
        Integer bookId = Integer.parseInt(createResponse.body().string());

        // Delete the book
        Response<ResponseBody> deleteResponse = deleteBook(bookId);
        assertNoContent(deleteResponse);

        // Verify book doesn't exist in any form
        Response<Book> getResponse = getBookById(bookId);
        assertNotFound(getResponse);

        Response<Book> getHistoryResponse = getBookWithLoanHistory(bookId);
        assertNotFound(getHistoryResponse);
    }

    private String generateUniqueIsbn() {
        return "978" + System.currentTimeMillis() % 10000000000L;
    }
}