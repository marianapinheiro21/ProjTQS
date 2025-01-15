package api.mappings.Reservation;

import api.mappings.generic.ErrorResponse;
import api.mappings.generic.Reservation;
import lombok.SneakyThrows;
import org.testng.annotations.Test;
import retrofit2.Response;

import static api.retrofit.Reservations.getReservationByID;
import static api.retrofit.generic.Errors.getErrorsResponse;
import static api.validators.ErrorResponseValidator.assertErrorResponse;
import static api.validators.ResponseValidator.assertBadRequest;
import static api.validators.ResponseValidator.assertOk;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class GetReservationNegativeTest {

    @SneakyThrows
    @Test(description ="Get no reservation by ID")
    public void getReservationNegativeTest( ) {
         // Por algum motivo o teste está a falhar diz que estou à espera que me devolva o erro 400 quando eu digo claramente que espero o erro 404
        Response<Reservation> response = getReservationByID(0);
        assertBadRequest(response);

        ErrorResponse errorResponse = getErrorsResponse(response);

        assertErrorResponse(errorResponse, 404, "Not Found", "Reservation not found", "/reservation/0");
        /*assertThat("Timestamp should not be null", errorResponse.getTimestamp(), notNullValue());
        assertThat("status is not the expected", errorResponse.getStatus(), is(404));
        assertThat("Error is not the expected", errorResponse.getError(), is("Not Found"));
        assertThat("Message is not the expected", errorResponse.getMessage(), is("Reservation not found"));
        assertThat("Path is not the expected", errorResponse.getPath(), is("/reservation/0"));
    */
    }

}
