package api.mappings.Book;

import api.mappings.generic.Book;
import api.mappings.generic.BookStatus;
import api.mappings.generic.ErrorResponse;
import api.retrofit.generic.Errors;
import okhttp3.ResponseBody;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;

import static api.retrofit.Books.createBook;
import static api.validators.ErrorResponseValidator.assertErrorResponse;
import static api.validators.ResponseValidator.assertBadRequest;

public class CreateBookNegativeTest {

    @Test(description = "Create book with empty title")
    public void createBookWithEmptyTitleTest() throws IOException {
        Book bookRequest = Book.builder()
                .title("")
                .author("Test Author")
                .isbn("9780544003415")
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Title is required", "/book");
    }

    @Test(description = "Create book with null title")
    public void createBookWithNullTitleTest() throws IOException {
        Book bookRequest = Book.builder()
                .title(null)
                .author("Test Author")
                .isbn("9780544003415")
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Title is required", "/book");
    }

    @Test(description = "Create book with empty author")
    public void createBookWithEmptyAuthorTest() throws IOException {
        Book bookRequest = Book.builder()
                .title("Test Book")
                .author("")
                .isbn("9780544003415")
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Author is required", "/book");
    }

    @Test(description = "Create book with null author")
    public void createBookWithNullAuthorTest() throws IOException {
        Book bookRequest = Book.builder()
                .title("Test Book")
                .author(null)
                .isbn("9780544003415")
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Author is required", "/book");
    }

    @Test(description = "Create book with invalid ISBN format")
    public void createBookWithInvalidIsbnFormatTest() throws IOException {
        Book bookRequest = Book.builder()
                .title("Test Book")
                .author("Test Author")
                .isbn("invalid-isbn")
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Invalid ISBN format", "/book");
    }

    @Test(description = "Create book with ISBN containing special characters")
    public void createBookWithSpecialCharactersIsbnTest() throws IOException {
        Book bookRequest = Book.builder()
                .title("Test Book")
                .author("Test Author")
                .isbn("978@#$%^&*")
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Invalid ISBN format", "/book");
    }

    @Test(description = "Create book with too short ISBN")
    public void createBookWithShortIsbnTest() throws IOException {
        Book bookRequest = Book.builder()
                .title("Test Book")
                .author("Test Author")
                .isbn("12345")
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Invalid ISBN format", "/book");
    }

    @Test(description = "Create book with too long ISBN")
    public void createBookWithLongIsbnTest() throws IOException {
        Book bookRequest = Book.builder()
                .title("Test Book")
                .author("Test Author")
                .isbn("97805440034151234567890")
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Invalid ISBN format", "/book");
    }

    @Test(description = "Create book with future edition year")
    public void createBookWithFutureYearTest() throws IOException {
        int futureYear = java.time.Year.now().getValue() + 1;
        Book bookRequest = Book.builder()
                .title("Test Book")
                .author("Test Author")
                .isbn("9780544003415")
                .editionYear(futureYear)
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Edition year cannot be in the future", "/book");
    }

    @Test(description = "Create book with invalid edition year (too old)")
    public void createBookWithTooOldYearTest() throws IOException {
        Book bookRequest = Book.builder()
                .title("Test Book")
                .author("Test Author")
                .isbn("9780544003415")
                .editionYear(1000)
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Edition year must be after 1450", "/book");
    }

    @Test(description = "Create book with invalid status")
    public void createBookWithInvalidStatusTest() throws IOException {
        Book bookRequest = Book.builder()
                .title("Test Book")
                .author("Test Author")
                .isbn("9780544003415")
                .status("INVALID_STATUS")
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Invalid book status", "/book");
    }

    @Test(description = "Create book with title exceeding maximum length")
    public void createBookWithTooLongTitleTest() throws IOException {
        String tooLongTitle = "T".repeat(256); // Assuming 255 is max length
        Book bookRequest = Book.builder()
                .title(tooLongTitle)
                .author("Test Author")
                .isbn("9780544003415")
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Title exceeds maximum length", "/book");
    }

    @Test(description = "Create book with description exceeding maximum length")
    public void createBookWithTooLongDescriptionTest() throws IOException {
        String tooLongDescription = "D".repeat(1001); // Assuming 1000 is max length
        Book bookRequest = Book.builder()
                .title("Test Book")
                .author("Test Author")
                .isbn("9780544003415")
                .description(tooLongDescription)
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Description exceeds maximum length", "/book");
    }

    @Test(description = "Create book with duplicate ISBN")
    public void createBookWithDuplicateIsbnTest() throws IOException {
        // First book creation
        Book firstBook = Book.builder()
                .title("First Book")
                .author("Test Author")
                .isbn("9780544003415")
                .build();
        createBook(firstBook);

        // Second book with same ISBN
        Book secondBook = Book.builder()
                .title("Second Book")
                .author("Another Author")
                .isbn("9780544003415")
                .build();

        Response<ResponseBody> response = createBook(secondBook);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "ISBN already exists", "/book");
    }

    @Test(description = "Create book with all null fields")
    public void createBookWithAllNullFieldsTest() throws IOException {
        Book bookRequest = Book.builder().build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Required fields missing", "/book");
    }

    @Test(description = "Create book with invalid characters in fields")
    public void createBookWithInvalidCharactersTest() throws IOException {
        Book bookRequest = Book.builder()
                .title("Test Book <script>alert('xss')</script>")
                .author("Test Author <script>alert('xss')</script>")
                .isbn("9780544003415")
                .description("Description <script>alert('xss')</script>")
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Invalid characters in fields", "/book");
    }

    @Test(description = "Create book with only whitespace in required fields")
    public void createBookWithWhitespaceFieldsTest() throws IOException {
        Book bookRequest = Book.builder()
                .title("   ")
                .author("   ")
                .isbn("9780544003415")
                .build();

        Response<ResponseBody> response = createBook(bookRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Required fields cannot be empty", "/book");
    }
}