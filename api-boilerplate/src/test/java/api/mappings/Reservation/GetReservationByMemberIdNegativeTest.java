package api.mappings.Reservation;

import api.mappings.generic.ErrorResponse;
import api.mappings.generic.Reservation;
import lombok.SneakyThrows;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.util.List;

import static api.retrofit.Reservations.*;
import static api.retrofit.generic.Errors.getErrorsResponse;
import static api.validators.ErrorResponseValidator.assertErrorResponse;
import static api.validators.ResponseValidator.assertBadRequest;

public class GetReservationByMemberIdNegativeTest {

    @SneakyThrows
    @Test(description ="Get no reservation by member's ID")
    public void getReservationNegativeTest1( ) {
        // Por algum motivo o teste está a falhar diz que estou à espera que me devolva o erro 400 quando eu digo claramente que espero o erro 404
        Response<List<Reservation>> response = getAllReservationByMemberId(0);
        assertBadRequest(response);

        ErrorResponse errorResponse = getErrorsResponse(response);

        assertErrorResponse(errorResponse, 404, "Not Found", "Member Id not found", "/reservation/0");

    }

    @SneakyThrows
    @Test(description ="Get no reservation by member's ID")
    public void getReservationNegativeTest2( ) {
        // Por algum motivo o teste está a falhar diz que estou à espera que me devolva o erro 400 quando eu digo claramente que espero o erro 404
        Response<List<Reservation>> response = getAllReservationByMemberIdNotActive(0);
        assertBadRequest(response);

        ErrorResponse errorResponse = getErrorsResponse(response);

        assertErrorResponse(errorResponse, 404, "Not Found", "Member Id not found", "/reservation/0");

    }

    @SneakyThrows
    @Test(description ="Get no reservation by member's ID")
    public void getReservationNegativeTest3( ) {
        // Por algum motivo o teste está a falhar diz que estou à espera que me devolva o erro 400 quando eu digo claramente que espero o erro 404
        Response<List<Reservation>> response = getAllReservationByMemberIdActive(0);
        assertBadRequest(response);

        ErrorResponse errorResponse = getErrorsResponse(response);

        assertErrorResponse(errorResponse, 404, "Not Found", "Member Id not found", "/reservation/0");

    }
}
