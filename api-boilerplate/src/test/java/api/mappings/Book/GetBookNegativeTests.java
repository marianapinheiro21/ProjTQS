package api.mappings.Book;

import api.mappings.generic.Book;
import api.mappings.generic.ErrorResponse;
import api.retrofit.generic.Errors;
import org.testng.annotations.Test;
import retrofit2.Response;

import static api.retrofit.Books.getBookById;
import static api.validators.ErrorResponseValidator.assertErrorResponse;
import static api.validators.ResponseValidator.assertNotFound;
import static api.validators.ResponseValidator.assertBadRequest;

public class GetBookNegativeTests {

    @Test(description = "Get Book with non-existent ID")
    public void getBookWithNonExistentIdTest() throws IOException {
        Response<Book> response = getBookById(99999);
        assertNotFound(response);

        ErrorResponse error = Errors.getErrorsResponse(response);
        assertErrorResponse(error, 404, "Not Found", "Book not found", "/book/99999");
    }

    @Test(description = "Get Book with invalid ID format")
    public void getBookWithInvalidIdFormatTest() throws IOException {
        Response<Book> response = getBookById(-1);
        assertBadRequest(response);

        ErrorResponse error = Errors.getErrorsResponse(response);
        assertErrorResponse(error, 400, "Bad Request", "Invalid book ID", "/book/-1");
    }
}