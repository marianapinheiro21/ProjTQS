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

public class GetReservationByBookIDNegativeTests {

    @SneakyThrows
    @Test(description ="Get no reservation by book's ID")
    public void getReservationByBookNegativeTest1( ) {
        // Por algum motivo o teste está a falhar diz que estou à espera que me devolva o erro 400 quando eu digo claramente que espero o erro 404
        Response<List<Reservation>> response = getAllReservationByBookId(0);
        assertBadRequest(response);

        ErrorResponse errorResponse = getErrorsResponse(response);

        assertErrorResponse(errorResponse, 404, "Not Found", "BookId not found", "/reservation/book/0");

    }

    @SneakyThrows
    @Test(description ="Get no reservation by book's ID")
    public void getReservationByBookNegativeTest2( ) {
        // Por algum motivo o teste está a falhar diz que estou à espera que me devolva o erro 400 quando eu digo claramente que espero o erro 404
        Response<List<Reservation>> response = getAllReservationByBookIdNotActive(0);
        assertBadRequest(response);

        ErrorResponse errorResponse = getErrorsResponse(response);

        assertErrorResponse(errorResponse, 404, "Not Found", "BookId not found", "/reservation/book/0");

    }

    @SneakyThrows
    @Test(description ="Get no reservation by book's ID")
    public void getReservationByBookNegativeTest3( ) {
        // Por algum motivo o teste está a falhar diz que estou à espera que me devolva o erro 400 quando eu digo claramente que espero o erro 404
        Response<List<Reservation>> response = getAllReservationByBookIdActive(0);
        assertBadRequest(response);

        ErrorResponse errorResponse = getErrorsResponse(response);

        assertErrorResponse(errorResponse, 404, "Not Found", "BookId not found", "/reservation/book/0");

    }
}
