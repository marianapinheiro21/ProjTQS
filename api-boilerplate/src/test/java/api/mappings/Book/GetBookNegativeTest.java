package api.mappings.Book;

import api.mappings.generic.Book;
import lombok.SneakyThrows;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.util.List;

import static api.retrofit.Books.*;
import static api.validators.ResponseValidator.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetBookNegativeTest {

    @Test(description = "Get book with non-existent ID")
    @SneakyThrows
    public void getBookNonExistentIdTest() {
        Response<Book> response = getBookById(99999);
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

    @Test(description = "Get book with invalid ID", dataProvider = "invalidIds")
    @SneakyThrows
    public void getBookInvalidIdTest(Integer invalidId) {
        Response<Book> response = getBookById(invalidId);
        assertBadRequest(response);
    }


    @Test(description = "Get books with invalid sorting parameters")
    @SneakyThrows
    public void getBookInvalidSortingTest() {

        Response<List<Book>> response1 = getBooksSorted("invalidField", "asc");
        assertBadRequest(response1);


        Response<List<Book>> response2 = getBooksSorted("title", "invalid");
        assertBadRequest(response2);
    }

    @Test(description = "Get books with SQL injection attempt")
    @SneakyThrows
    public void getBookSqlInjectionTest() {
        Response<List<Book>> response = searchBooks("'; DROP TABLE books; --");
        assertBadRequest(response);
    }

    @Test(description = "Get books with very long search term")
    @SneakyThrows
    public void getBookLongSearchTermTest() {
        String veryLongTerm = "a".repeat(1000);
        Response<List<Book>> response = searchBooks(veryLongTerm);
        assertBadRequest(response);
    }

    @Test(description = "Get books with special characters in search")
    @SneakyThrows
    public void getBookSpecialCharsSearchTest() {
        Response<List<Book>> response = searchBooks("<script>alert('xss')</script>");
        assertBadRequest(response);
    }

    @Test(description = "Get books with invalid status")
    @SneakyThrows
    public void getBookInvalidStatusTest() {
        Response<List<Book>> response = getBooksByStatus("INVALID_STATUS");
        assertBadRequest(response);
    }

    @Test(description = "Get books with multiple invalid parameters")
    @SneakyThrows
    public void getBookMultipleInvalidParamsTest() {
        Response<List<Book>> response = getBooksWithMultipleParams(-1, 0, "invalid", "invalid", "'; DROP TABLE;--");
        assertBadRequest(response);
    }

    @Test(description = "Get book with non-numeric ID")
    @SneakyThrows
    public void getBookNonNumericIdTest() {
        Response<Book> response = getBookByNonNumericId("abc");
        assertBadRequest(response);
    }
}