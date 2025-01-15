package api.mappings.Book;

import api.mappings.generic.Book;
import api.mappings.generic.BookStatus;
import api.mappings.generic.ErrorResponse;
import api.retrofit.generic.Errors;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;

import static api.retrofit.Books.*;
import static api.validators.ResponseValidator.*;
import static api.validators.ErrorResponseValidator.assertErrorResponse;

public class UpdateBookNegativeTests {

    private Integer bookId;

    @BeforeMethod
    public void setup() throws IOException {
        Book book = createDefaultTestBook();
        bookId = createBookAndGetId(book);
    }

    @AfterMethod
    public void cleanup() {
        if (bookId != null) {
            deleteBook(bookId);
        }
    }

    @Test(description = "Update book with non-existent ID")
    public void updateNonExistentBookTest() throws IOException {
        Book bookUpdate = createDefaultTestBook();
        Response<Book> response = updateBook(99999, bookUpdate);
        assertNotFound(response);

        ErrorResponse error = Errors.getErrorsResponse(response);
        assertErrorResponse(error, 404, "Not Found",
                "Book not found", "/book/99999");
    }

    @Test(description = "Update book with invalid status transition")
    public void updateInvalidStatusTransitionTest() throws IOException {
        // Primeiro reservar o livro
        Book reserveUpdate = Book.builder()
                .status(BookStatus.RESERVED.toString())
                .build();
        updateBook(bookId, reserveUpdate);

        // Tentar mudar direto para AVAILABLE (deve passar por NOT_AVAILABLE primeiro)
        Book invalidUpdate = Book.builder()
                .status(BookStatus.AVAILABLE.toString())
                .build();
        Response<Book> response = updateBook(bookId, invalidUpdate);
        assertBadRequest(response);

        ErrorResponse error = Errors.getErrorsResponse(response);
        assertErrorResponse(error, 400, "Bad Request",
                "Invalid status transition", "/book/" + bookId);
    }

    @Test(description = "Update book with duplicate ISBN")
    public void updateBookWithDuplicateIsbnTest() throws IOException {
        // Criar outro livro primeiro
        Book otherBook = createDefaultTestBook();
        Integer otherId = createBookAndGetId(otherBook);

        try {
            // Tentar atualizar o ISBN do primeiro livro para o mesmo do segundo
            Book updateRequest = Book.builder()
                    .isbn(otherBook.getIsbn())
                    .build();

            Response<Book> response = updateBook(bookId, updateRequest);
            assertConflict(response);

            ErrorResponse error = Errors.getErrorsResponse(response);
            assertErrorResponse(error, 409, "Conflict",
                    "ISBN already exists", "/book/" + bookId);
        } finally {
            deleteBook(otherId);
        }
    }

    @Test(description = "Update book with invalid year")
    public void updateBookWithInvalidYearTest() throws IOException {
        Book updateRequest = Book.builder()
                .editionYear(2025) // ano futuro
                .build();

        Response<Book> response = updateBook(bookId, updateRequest);
        assertBadRequest(response);
    }
}