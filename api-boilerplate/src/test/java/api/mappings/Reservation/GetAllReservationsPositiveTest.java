package api.mappings.Reservation;

import api.mappings.generic.Book;
import api.mappings.generic.Reservation;
import okhttp3.ResponseBody;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static api.retrofit.Books.*;
import static api.retrofit.Reservations.*;
import static api.validators.ResponseValidator.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetAllReservationsPositiveTest {
    private List<Integer> bookIds;
    private List<Integer> reservationIds;

    @BeforeMethod
    public void setup() {
        bookIds = new ArrayList<>();
        reservationIds = new ArrayList<>();
    }

    @AfterMethod
    public void cleanup() throws IOException {
        for (Integer reservationId : reservationIds) {
            Response<ResponseBody> returnResponse = returnBook(reservationId);
            assertOk(returnResponse);
        }


        for (Integer bookId : bookIds) {
            Response<ResponseBody> deleteResponse = deleteBook(bookId);
            assertOk(deleteResponse);
        }
    }

    @Test(description = "Get all reservations - empty list")
    public void getAllReservationsEmptyTest() throws IOException {
        Response<List<Reservation>> response = getAllReservation();
        assertOk(response);

        List<Reservation> reservations = response.body();
        assertThat(reservations, notNullValue());
        assertThat(reservations.isEmpty(), is(true));
    }

    @Test(description = "Get all reservations - with multiple reservations")
    public void getAllReservationsMultipleTest() throws IOException {
        createTestReservations(3);

        Response<List<Reservation>> response = getAllReservation();
        assertOk(response);

        List<Reservation> reservations = response.body();
        assertThat(reservations, notNullValue());
        assertThat(reservations.size(), greaterThanOrEqualTo(3));


        for (Reservation reservation : reservations) {
            assertValidReservationStructure(reservation);
        }
    }

    @Test(description = "Get all reservations - validate reservation structure")
    public void getAllReservationsStructureTest() throws IOException {
        // Criar uma reserva para teste
        createTestReservations(1);

        Response<List<Reservation>> response = getAllReservation();
        assertOk(response);

        List<Reservation> reservations = response.body();
        assertThat(reservations, notNullValue());
        assertThat(reservations, not(empty()));

        Reservation reservation = reservations.stream()
                .filter(r -> reservationIds.contains(r.getId()))
                .findFirst()
                .orElseThrow();

        assertValidReservationStructure(reservation);
    }

    private void createTestReservations(int count) throws IOException {
        for (int i = 0; i < count; i++) {
            // Criar livro
            Book testBook = Book.builder()
                    .title("Test Book " + i)
                    .author("Test Author")
                    .isbn(generateUniqueIsbn())
                    .build();

            Response<ResponseBody> bookResponse = createBook(testBook);
            assertCreated(bookResponse);
            Integer bookId = Integer.parseInt(bookResponse.body().string());
            bookIds.add(bookId);


            Response<ResponseBody> reservationResponse = createReservation(generateMemberId(), bookId);
            assertCreated(reservationResponse);
            Integer reservationId = Integer.parseInt(reservationResponse.body().string());
            reservationIds.add(reservationId);
        }
    }

    private void assertValidReservationStructure(Reservation reservation) {
        assertThat("ID deve ser não nulo",
                reservation.getId(), notNullValue());

        assertThat("MemberID deve ser não nulo",
                reservation.getMemberId(), notNullValue());

        assertThat("BookID deve ser não nulo",
                reservation.getBookId(), notNullValue());

        assertThat("ReservationDate deve estar no formato correto",
                reservation.getReservationDate(), matchesPattern(
                        "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"
                ));

        assertThat("ReturnDate deve estar no formato correto",
                reservation.getReturnDate(), matchesPattern(
                        "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"
                ));
    }

    private String generateUniqueIsbn() {
        return "978" + System.currentTimeMillis() % 10000000000L;
    }

    private Integer generateMemberId() {
        return (int)(System.currentTimeMillis() % 1000000);
    }
}