package api.mappings.Reservation;

import api.mappings.generic.ErrorResponse;
import org.testng.annotations.Test;
import retrofit2.Response;
import java.io.IOException;
import java.util.List;

import static api.validators.ResponseValidator.*;
import static api.validators.ErrorResponseValidator.assertErrorResponse;

public class GetBookReservationsNegativeTest {

    @Test(description = "Get reservations for non-existent book")
    public void getNonExistentBookReservationsTest() throws IOException {
        Response<List<Reservation>> response = ReservationAPI.getBookReservations(99999, null);
        assertNotFound(response);

        ErrorResponse errorResponse = ErrorResponse.from(response);
        assertErrorResponse(errorResponse, 404, "Not Found",
                "Book Id not found", "/reservation/book/99999");
    }

    @Test(description = "Get reservations with invalid book ID")
    public void getReservationsInvalidBookIdTest() throws IOException {
        Response<List<Reservation>> response = ReservationAPI.getBookReservations(-1, null);
        assertBadRequest(response);

        ErrorResponse errorResponse = ErrorResponse.from(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Invalid book ID", "/reservation/book/-1");
    }

    @Test(description = "Get reservations with invalid active parameter")
    public void getReservationsInvalidActiveParameterTest() throws IOException {
        Response<List<Reservation>> response = ReservationAPI.getBookReservationsInvalidActive(1);
        assertBadRequest(response);

        ErrorResponse errorResponse = ErrorResponse.from(response);
        assertErrorResponse(errorResponse, 400, "Bad Request",
                "Invalid active parameter", "/reservation/book/1");
    }
}