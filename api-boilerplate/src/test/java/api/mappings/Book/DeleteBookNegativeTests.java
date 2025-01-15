package api.mappings.Book;

import api.mappings.generic.Book;
import api.mappings.generic.BookStatus;
import api.mappings.generic.ErrorResponse;
import api.retrofit.generic.Errors;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

import static api.retrofit.Books.*;
import static api.validators.ResponseValidator.*;
import static api.validators.ErrorResponseValidator.assertErrorResponse;

public class DeleteBookNegativeTests {

    private Integer bookId;
    private Book testBook;

    @BeforeMethod
    public void setup() throws IOException {
        testBook = createDefaultTestBook();
        bookId = createBookAndGetId(testBook);
    }

    @Test(description = "Try to delete non-existent book")
    public void deleteNonExistentBookTest() throws IOException {
        Response<ResponseBody> response = deleteBook(99999);
        assertNotFound(response);

        ErrorResponse error = Errors.getErrorsResponse(response);
        assertErrorResponse(error, 404, "Not Found",
                "Book not found", "/book/99999");
    }

    @Test(description = "Try to delete reserved book")
    public void deleteReservedBookTest() throws IOException {

        Book updateRequest = Book.builder()
                .status(BookStatus.RESERVED.toString())
                .build();
        updateBook(bookId, updateRequest);


        Response<ResponseBody> response = deleteBook(bookId);
        assertConflict(response);

        ErrorResponse error = Errors.getErrorsResponse(response);
        assertErrorResponse(error, 409, "Conflict",
                "Cannot delete reserved book", "/book/" + bookId);


        updateRequest.setStatus(BookStatus.AVAILABLE.toString());
        updateBook(bookId, updateRequest);
        deleteBook(bookId);
    }

    @Test(description = "Try to delete book with invalid ID")
    public void deleteBookWithInvalidIdTest() throws IOException {
        Response<ResponseBody> response = deleteBook(-1);
        assertBadRequest(response);

        ErrorResponse error = Errors.getErrorsResponse(response);
        assertErrorResponse(error, 400, "Bad Request",
                "Invalid book ID", "/book/-1");
    }

    @Test(description = "Try to delete already deleted book")
    public void deleteAlreadyDeletedBookTest() throws IOException {

        Response<ResponseBody> firstDeleteResponse = deleteBook(bookId);
        assertNoContent(firstDeleteResponse);


        Response<ResponseBody> secondDeleteResponse = deleteBook(bookId);
        assertNotFound(secondDeleteResponse);

        ErrorResponse error = Errors.getErrorsResponse(secondDeleteResponse);
        assertErrorResponse(error, 404, "Not Found",
                "Book not found", "/book/" + bookId);
    }

    @Test(description = "Try to delete book with null ID")
    public void deleteBookWithNullIdTest() throws IOException {
        Response<ResponseBody> response = deleteBook(null);
        assertBadRequest(response);

        ErrorResponse error = Errors.getErrorsResponse(response);
        assertErrorResponse(error, 400, "Bad Request",
                "Book ID cannot be null", "/book/null");
    }

    @Test(description = "Try to delete book with ongoing loan")
    public void deleteBookWithOngoingLoanTest() throws IOException {

        createLoanForBook(bookId);

        Response<ResponseBody> response = deleteBook(bookId);
        assertConflict(response);

        ErrorResponse error = Errors.getErrorsResponse(response);
        assertErrorResponse(error, 409, "Conflict",
                "Cannot delete book with active loan", "/book/" + bookId);


        cancelLoanForBook(bookId);
        deleteBook(bookId);
    }
}