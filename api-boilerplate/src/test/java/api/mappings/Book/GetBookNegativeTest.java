package api.mappings.Book;

import api.mappings.generic.Book;
import api.mappings.generic.ErrorResponse;
import api.retrofit.generic.Errors;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

import static api.retrofit.Books.*;
import static api.validators.ResponseValidator.*;
import static api.validators.ErrorResponseValidator.assertErrorResponse;

public class GetBookNegativeTest {

    @Test(description = "Get book with non-existent ID")
    public void getNonExistentBookTest() throws IOException {
        Integer nonExistentId = 999999;
        Response<Book> response = getBookById(nonExistentId);
        assertNotFound(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 404, "Not Found",
                "Book not found", "/book/" + nonExistentId);
    }

    @Test(description = "Get book with negative ID")
    public void getBookWithNegativeIdTest() throws IOException {
        Integer negativeId = -1;
        Response<Book> response = getBookById(negativeId);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Invalid book ID", "/book/" + negativeId);
    }

    @Test(description = "Get book with zero ID")
    public void getBookWithZeroIdTest() throws IOException {
        Integer zeroId = 0;
        Response<Book> response = getBookById(zeroId);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Invalid book ID", "/book/" + zeroId);
    }

    @Test(description = "Get books with invalid status")
    public void getBooksWithInvalidStatusTest() throws IOException {
        String invalidStatus = "INVALID_STATUS";
        Response<List<Book>> response = getBooksByStatus(invalidStatus);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Invalid book status", "/books/status/" + invalidStatus);
    }

    @Test(description = "Get books with empty status")
    public void getBooksWithEmptyStatusTest() throws IOException {
        Response<List<Book>> response = getBooksByStatus("");
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Status cannot be empty", "/books/status");
    }

    @Test(description = "Get books by invalid year")
    public void getBooksWithInvalidYearTest() throws IOException {
        int futureYear = java.time.Year.now().getValue() + 1;
        Response<List<Book>> response = getBooksByYear(futureYear);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Invalid year", "/books/year/" + futureYear);
    }

    @Test(description = "Get books by year before printing was invented")
    public void getBooksWithTooOldYearTest() throws IOException {
        int tooOldYear = 1000;
        Response<List<Book>> response = getBooksByYear(tooOldYear);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Year must be after 1450", "/books/year/" + tooOldYear);
    }

    @Test(description = "Search books with empty keyword")
    public void searchBooksWithEmptyKeywordTest() throws IOException {
        Response<List<Book>> response = searchBooksByTitle("");
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Search keyword cannot be empty", "/books/search");
    }

    @Test(description = "Search books with too short keyword")
    public void searchBooksWithShortKeywordTest() throws IOException {
        Response<List<Book>> response = searchBooksByTitle("a");
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Search keyword must be at least 2 characters", "/books/search");
    }

    @Test(description = "Get books with invalid pagination parameters")
    public void getBooksWithInvalidPaginationTest() throws IOException {
        Response<List<Book>> response = getBooksWithPagination(-1, 0);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Invalid pagination parameters", "/books");
    }

    @Test(description = "Get book loan history with invalid ID")
    public void getBookLoanHistoryWithInvalidIdTest() throws IOException {
        Integer invalidId = -1;
        Response<Book> response = getBookWithLoanHistory(invalidId);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Invalid book ID", "/book/history/" + invalidId);
    }

    @Test(description = "Get books by invalid author name")
    public void getBooksWithInvalidAuthorTest() throws IOException {
        String invalidAuthor = "";
        Response<List<Book>> response = getBooksByAuthor(invalidAuthor);
        assertBadRequest(response);

        ErrorResponse errorResponse = Errors.getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Author name cannot be empty", "/books/author");
    }
}