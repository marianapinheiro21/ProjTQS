package api.mappings.Reservation;

import api.mappings.generic.ErrorResponse;
import api.mappings.generic.Reservation;
import lombok.SneakyThrows;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

import static api.retrofit.Reservations.*;
import static api.validators.ResponseValidator.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetBookReservationsNegativeTest {

    @Test(description = "Get reservations for non-existent book")
    @SneakyThrows
    public void getNonExistentBookReservationsTest() {
        Response<List<Reservation>> response = getAllReservationByBookId(99999);
        assertNotFound(response);

        assertThat(response.code(), is(404));
        assertThat(response.message(), is("Not Found"));
    }

    @Test(description = "Get reservations with invalid book ID")
    @SneakyThrows
    public void getReservationsInvalidBookIdTest() {
        Response<List<Reservation>> response = getAllReservationByBookId(-1);
        assertBadRequest(response);

        assertThat(response.code(), is(400));
        assertThat(response.message(), is("Bad Request"));
    }

    @Test(description = "Get reservations with invalid active parameter")
    @SneakyThrows
    public void getReservationsInvalidActiveParameterTest() {
        // Assumindo que temos um livro válido com ID 1
        Response<Reservation> response = getAllReservationByBookId(1);
        assertBadRequest(response);

        assertThat(response.code(), is(400));
        assertThat(response.message(), is("Bad Request"));
    }

    @Test(description = "Get reservations for book with zero ID")
    @SneakyThrows
    public void getReservationsZeroIdTest() {
        Response<List<Reservation>> response = getAllReservationByBookId(0);
        assertBadRequest(response);

        assertThat(response.code(), is(400));
        assertThat(response.message(), is("Bad Request"));
    }

    @Test(description = "Get reservations for book with very large ID")
    @SneakyThrows
    public void getReservationsLargeIdTest() {
        Response<List<Reservation>> response = getAllReservationByBookId(Integer.MAX_VALUE);
        assertNotFound(response);

        assertThat(response.code(), is(404));
        assertThat(response.message(), is("Not Found"));
    }
}