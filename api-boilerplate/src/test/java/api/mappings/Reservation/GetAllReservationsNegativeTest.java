package api.mappings.Reservation;

import api.mappings.generic.Reservation;
import lombok.SneakyThrows;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.util.List;

import static api.retrofit.Reservations.*;
import static api.validators.ResponseValidator.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetAllReservationsNegativeTest {

    @Test(description = "Get all reservations - server error simulation")
    @SneakyThrows
    public void getAllReservationsServerErrorTest() {

        Response<List<Reservation>> response = getAllReservation();

        if (response.code() == 500) {
            assertThat(response.code(), is(500));
            assertThat(response.message(), is("Internal Server Error"));
        } else {
            assertOk(response);
        }
    }

    @Test(description = "Get all reservations - unauthorized access")
    @SneakyThrows
    public void getAllReservationsUnauthorizedTest() {

        Response<List<Reservation>> response = getAllReservation();

        if (response.code() == 401) {
            assertThat(response.code(), is(401));
            assertThat(response.message(), is("Unauthorized"));
        } else {
            assertOk(response);
        }
    }
}