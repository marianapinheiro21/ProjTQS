package api.mappings.Reservation;

import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.testng.annotations.Test;
import retrofit2.Response;

import static api.retrofit.Reservations.*;
import static api.validators.ResponseValidator.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ReturnBookNegativeTest {

    @Test(description = "Return non-existent reservation")
    @SneakyThrows
    public void returnNonExistentReservationTest() {
        Response<ResponseBody> response = returnBook(99999);
        assertNotFound(response);

        assertThat(response.code(), is(404));
        assertThat(response.message(), is("Not Found"));
    }

    @Test(description = "Return book with invalid reservation ID")
    @SneakyThrows
    public void returnBookInvalidIdTest() {
        Response<ResponseBody> response = returnBook(-1);
        assertBadRequest(response);

        assertThat(response.code(), is(400));
        assertThat(response.message(), is("Bad Request"));
    }

    @Test(description = "Return book with zero ID")
    @SneakyThrows
    public void returnBookZeroIdTest() {
        Response<ResponseBody> response = returnBook(0);
        assertBadRequest(response);

        assertThat(response.code(), is(400));
        assertThat(response.message(), is("Bad Request"));
    }

    @Test(description = "Return already returned book")
    @SneakyThrows
    public void returnAlreadyReturnedBookTest() {
        // Primeiro precisamos criar uma reserva e retornar o livro
        Integer memberId = generateMemberId();
        Integer bookId = createTestBook();

        Response<ResponseBody> createResponse = createReservation(memberId, bookId);
        assertCreated(createResponse);
        Integer reservationId = Integer.parseInt(createResponse.body().string());

        // Primeira devolução
        Response<ResponseBody> firstReturnResponse = returnBook(reservationId);
        assertThat(firstReturnResponse.code(), is(204));

        // Segunda tentativa de devolução
        Response<ResponseBody> secondReturnResponse = returnBook(reservationId);
        assertBadRequest(secondReturnResponse);

        assertThat(secondReturnResponse.code(), is(400));
        assertThat(secondReturnResponse.message(), is("Bad Request"));

        // Cleanup
        deleteTestBook(bookId);
    }

    private Integer createTestBook() throws IOException {
        Book testBook = Book.builder()
                .title("Test Book")
                .author("Test Author")
                .isbn(generateUniqueIsbn())
                .build();

        Response<ResponseBody> response = createBook(testBook);
        return Integer.parseInt(response.body().string());
    }

    private void deleteTestBook(Integer bookId) throws IOException {
        deleteBook(bookId);
    }

    private String generateUniqueIsbn() {
        return "978" + System.currentTimeMillis() % 10000000000L;
    }

    private Integer generateMemberId() {
        return (int)(System.currentTimeMillis() % 1000000);
    }
}