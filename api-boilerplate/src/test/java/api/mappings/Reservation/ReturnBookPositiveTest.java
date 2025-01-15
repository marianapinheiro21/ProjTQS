package api.mappings.Reservation;

import api.mappings.generic.Book;
import api.mappings.generic.Reservation;
import okhttp3.ResponseBody;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import static api.retrofit.Books.*;
import static api.retrofit.Reservations.*;
import static api.validators.ResponseValidator.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ReturnBookPositiveTest {
    private Integer bookId;
    private Integer reservationId;

    @BeforeMethod
    public void setup() throws IOException {
        // Criar um livro
        Book testBook = Book.builder()
                .title("Test Book")
                .author("Test Author")
                .isbn(generateUniqueIsbn())
                .build();

        Response<ResponseBody> createBookResponse = createBook(testBook);
        assertCreated(createBookResponse);
        bookId = Integer.parseInt(createBookResponse.body().string());

        // Criar uma reserva
        Response<ResponseBody> createReservationResponse = createReservation(generateMemberId(), bookId);
        assertCreated(createReservationResponse);
        reservationId = Integer.parseInt(createReservationResponse.body().string());
    }

    @AfterMethod
    public void cleanup() throws IOException {
        if (bookId != null) {
            Response<ResponseBody> deleteResponse = deleteBook(bookId);
            assertOk(deleteResponse);
        }
    }

    @Test(description = "Return book successfully")
    public void returnBookSuccessfullyTest() throws IOException {
        Response<ResponseBody> response = returnBook(reservationId);
        assertThat(response.code(), is(204));

        // Verificar se a reserva foi atualizada
        Response<Reservation> checkResponse = getReservationByID(reservationId);
        assertOk(checkResponse);

        Reservation reservation = checkResponse.body();
        assertThat(reservation.getId(), is(reservationId));
        assertThat(reservation.getBookId(), is(bookId));
        // Aqui podemos adicionar mais verificações específicas do estado da reserva após devolução
    }

    private String generateUniqueIsbn() {
        return "978" + System.currentTimeMillis() % 10000000000L;
    }

    private Integer generateMemberId() {
        return (int)(System.currentTimeMillis() % 1000000);
    }
}