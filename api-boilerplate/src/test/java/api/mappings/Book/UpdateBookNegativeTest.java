package api.mappings.Book;

import api.mappings.generic.Book;
import api.mappings.generic.BookStatus;
import api.mappings.generic.ErrorResponse;
import api.retrofit.generic.Errors;
import okhttp3.ResponseBody;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;

import static api.retrofit.Books.*;
import static api.validators.ResponseValidator.*;
import static api.validators.ErrorResponseValidator.assertErrorResponse;

public class UpdateBookNegativeTest {

    @Test(description = "Update non-existent book")
    public void updateNonExistentBookTest() throws IOException {
        Book updateRequest = Book.builder()
                .title("Updated Title")
                .build();

        Response<Book> response = updateBook(99999, updateRequest);
        assertNotFound(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 404, "Not Found",
                "Book not found", "/book/99999");
    }

    @Test(description = "Update book with invalid ID")
    public void updateBookWithInvalidIdTest() throws IOException {
        Book updateRequest = Book.builder()
                .title("Updated Title")
                .build();

        Response<Book> response = updateBook(-1, updateRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Invalid book ID", "/book/-1");
    }

    @Test(description = "Update book with empty title")
    public void updateBookWithEmptyTitleTest() throws IOException {
        // Primeiro criar um livro válido
        Book originalBook = createValidBook();
        Integer bookId = Integer.parseInt(createBook(originalBook).body().string());

        Book updateRequest = Book.builder()
                .title("")
                .build();

        Response<Book> response = updateBook(bookId, updateRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Title cannot be empty", "/book/" + bookId);

        // Cleanup
        deleteBook(bookId);
    }

    @Test(description = "Update book with invalid status")
    public void updateBookWithInvalidStatusTest() throws IOException {
        Book originalBook = createValidBook();
        Integer bookId = Integer.parseInt(createBook(originalBook).body().string());

        Book updateRequest = Book.builder()
                .status("INVALID_STATUS")
                .build();

        Response<Book> response = updateBook(bookId, updateRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Invalid book status", "/book/" + bookId);

        deleteBook(bookId);
    }

    @Test(description = "Update book with future edition year")
    public void updateBookWithFutureYearTest() throws IOException {
        Book originalBook = createValidBook();
        Integer bookId = Integer.parseInt(createBook(originalBook).body().string());

        int futureYear = java.time.Year.now().getValue() + 1;
        Book updateRequest = Book.builder()
                .editionYear(futureYear)
                .build();

        Response<Book> response = updateBook(bookId, updateRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Edition year cannot be in the future", "/book/" + bookId);

        deleteBook(bookId);
    }

    @Test(description = "Update book with title exceeding maximum length")
    public void updateBookWithTooLongTitleTest() throws IOException {
        Book originalBook = createValidBook();
        Integer bookId = Integer.parseInt(createBook(originalBook).body().string());

        String tooLongTitle = "T".repeat(256); // Assuming 255 is max length
        Book updateRequest = Book.builder()
                .title(tooLongTitle)
                .build();

        Response<Book> response = updateBook(bookId, updateRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Title exceeds maximum length", "/book/" + bookId);

        deleteBook(bookId);
    }

    @Test(description = "Update book with description exceeding maximum length")
    public void updateBookWithTooLongDescriptionTest() throws IOException {
        Book originalBook = createValidBook();
        Integer bookId = Integer.parseInt(createBook(originalBook).body().string());

        String tooLongDescription = "D".repeat(1001); // Assuming 1000 is max length
        Book updateRequest = Book.builder()
                .description(tooLongDescription)
                .build();

        Response<Book> response = updateBook(bookId, updateRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Description exceeds maximum length", "/book/" + bookId);

        deleteBook(bookId);
    }

    @Test(description = "Update book while it's being loaned")
    public void updateLoanedBookTest() throws IOException {
        Book originalBook = createValidBook();
        Integer bookId = Integer.parseInt(createBook(originalBook).body().string());

        // Simular empréstimo do livro
        updateBook(bookId, Book.builder().status(BookStatus.LOANED.toString()).build());

        Book updateRequest = Book.builder()
                .title("New Title")
                .build();

        Response<Book> response = updateBook(bookId, updateRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Cannot update loaned book", "/book/" + bookId);

        deleteBook(bookId);
    }

    @Test(description = "Update book with invalid characters in fields")
    public void updateBookWithInvalidCharactersTest() throws IOException {
        Book originalBook = createValidBook();
        Integer bookId = Integer.parseInt(createBook(originalBook).body().string());

        Book updateRequest = Book.builder()
                .title("Test Title <script>alert('xss')</script>")
                .description("Test Description <script>alert('xss')</script>")
                .build();

        Response<Book> response = updateBook(bookId, updateRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Invalid characters in fields", "/book/" + bookId);

        deleteBook(bookId);
    }

    @Test(description = "Update book with null request body")
    public void updateBookWithNullBodyTest() throws IOException {
        Book originalBook = createValidBook();
        Integer bookId = Integer.parseInt(createBook(originalBook).body().string());

        Response<Book> response = updateBook(bookId, null);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Request body cannot be null", "/book/" + bookId);

        deleteBook(bookId);
    }

    // Helper method to create a valid book
    private Book createValidBook() {
        return Book.builder()
                .title("Test Book")
                .author("Test Author")
                .isbn("978" + System.currentTimeMillis() % 10000000000L)
                .status(BookStatus.AVAILABLE.toString())
                .build();
    }
}