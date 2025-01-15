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

public class DeleteBookNegativeTest {

    @Test(description = "Delete non-existent book")
    public void deleteNonExistentBookTest() throws IOException {
        Response<ResponseBody> response = deleteBook(99999);
        assertNotFound(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 404, "Not Found",
                "Book not found", "/book/99999");
    }

    @Test(description = "Delete book with invalid ID")
    public void deleteBookWithInvalidIdTest() throws IOException {
        Response<ResponseBody> response = deleteBook(-1);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Invalid book ID", "/book/-1");
    }

    @Test(description = "Delete loaned book")
    public void deleteLoanedBookTest() throws IOException {
        // Create a book
        Book book = Book.builder()
                .title("Loaned Book")
                .author("Test Author")
                .isbn(generateUniqueIsbn())
                .status(BookStatus.AVAILABLE.toString())
                .build();

        Response<ResponseBody> createResponse = createBook(book);
        Integer bookId = Integer.parseInt(createResponse.body().string());

        // Update status to LOANED
        updateBook(bookId, Book.builder().status(BookStatus.LOANED.toString()).build());

        // Try to delete loaned book
        Response<ResponseBody> deleteResponse = deleteBook(bookId);
        assertBadRequest(deleteResponse);

        ErrorResponse errorResponse = Errors.getErrorsResponse(deleteResponse);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Cannot delete loaned book", "/book/" + bookId);

        // Cleanup
        updateBook(bookId, Book.builder().status(BookStatus.AVAILABLE.toString()).build());
        deleteBook(bookId);
    }

    @Test(description = "Delete reserved book")
    public void deleteReservedBookTest() throws IOException {
        // Create a book
        Book book = Book.builder()
                .title("Reserved Book")
                .author("Test Author")
                .isbn(generateUniqueIsbn())
                .status(BookStatus.AVAILABLE.toString())
                .build();

        Response<ResponseBody> createResponse = createBook(book);
        Integer bookId = Integer.parseInt(createResponse.body().string());

        // Update status to RESERVED
        updateBook(bookId, Book.builder().status(BookStatus.RESERVED.toString()).build());

        // Try to delete reserved book
        Response<ResponseBody> deleteResponse = deleteBook(bookId);
        assertBadRequest(deleteResponse);

        ErrorResponse errorResponse = Errors.getErrorsResponse(deleteResponse);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Cannot delete reserved book", "/book/" + bookId);

        // Cleanup
        updateBook(bookId, Book.builder().status(BookStatus.AVAILABLE.toString()).build());
        deleteBook(bookId);
    }

    @Test(description = "Delete book with active reservations")
    public void deleteBookWithActiveReservationsTest() throws IOException {
        // Create a book
        Book book = Book.builder()
                .title("Book with Reservations")
                .author("Test Author")
                .isbn(generateUniqueIsbn())
                .status(BookStatus.RESERVED.toString())
                .build();

        Response<ResponseBody> createResponse = createBook(book);
        Integer bookId = Integer.parseInt(createResponse.body().string());

        // Simulate adding a reservation
        // (This depende da implementação específica do sistema de reservas)

        // Try to delete book with reservations
        Response<ResponseBody> deleteResponse = deleteBook(bookId);
        assertBadRequest(deleteResponse);

        ErrorResponse errorResponse = Errors.getErrorsResponse(deleteResponse);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Cannot delete book with active reservations", "/book/" + bookId);

        // Cleanup
        updateBook(bookId, Book.builder().status(BookStatus.AVAILABLE.toString()).build());
        deleteBook(bookId);
    }

    @Test(description = "Delete book with loan history")
    public void deleteBookWithLoanHistoryTest() throws IOException {
        // Create a book
        Book book = Book.builder()
                .title("Book with History")
                .author("Test Author")
                .isbn(generateUniqueIsbn())
                .status(BookStatus.AVAILABLE.toString())
                .build();

        Response<ResponseBody> createResponse = createBook(book);
        Integer bookId = Integer.parseInt(createResponse.body().string());

        // Simulate loan history
        updateBook(bookId, Book.builder().status(BookStatus.LOANED.toString()).build());
        updateBook(bookId, Book.builder().status(BookStatus.AVAILABLE.toString()).build());

        // Verify deletion is still possible despite loan history
        Response<ResponseBody> deleteResponse = deleteBook(bookId);
        assertNoContent(deleteResponse);

        Response<Book> getResponse = getBookById(bookId);
        assertNotFound(getResponse);
    }

    private String generateUniqueIsbn() {
        return "978" + System.currentTimeMillis() % 10000000000L;
    }
}