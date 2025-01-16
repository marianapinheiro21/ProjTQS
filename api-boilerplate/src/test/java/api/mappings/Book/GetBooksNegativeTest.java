package api.mappings.Book;

import api.mappings.generic.Book;
import api.mappings.generic.ErrorResponse;
import api.mappings.generic.Member;
import api.retrofit.generic.Errors;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;

import static api.retrofit.Books.getBookById;
import static api.retrofit.Members.getMemberByID;
import static api.validators.ErrorResponseValidator.assertErrorResponse;
import static api.validators.ResponseValidator.assertNotFound;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class GetBooksNegativeTest {

    @Test(description = "Failing to get a book")
    public void getBookNegativeTest() throws IOException {
        Integer id = 0;
        Response<Book> response = getBookById(0);
        assertNotFound(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertThat("Timestamp should not be null", errorResponse.getTimestamp(), notNullValue());
        assertThat("status is not the expected", errorResponse.getStatus(), is(404));
        assertThat("Error is not the expected", errorResponse.getError(), is("Not Found"));
        assertThat("Message is not the expected", errorResponse.getMessage(), is("Book not found"));
        assertThat("Path is not the expected", errorResponse.getPath(), is(String.format("/book/%d", id)));

        assertErrorResponse(errorResponse, 404, "Not Found", "Book not found", String.format("/book/%d", id));
    }
}
