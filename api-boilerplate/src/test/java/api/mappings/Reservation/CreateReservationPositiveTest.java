package api.mappings.Reservation;

import api.mappings.generic.Member;
import api.mappings.generic.Book;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDate;

import static api.retrofit.Members.getMemberByID;
import static api.retrofit.Reservations.createReservation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CreateReservationPositiveTest {

    @SneakyThrows
    @Test(description = "Create new reservation with success")
    public void createReservationSuccess(){ //works, not properly tested yet
        Integer memberId = 2;
        Integer bookId = 1;

        Response<ResponseBody> reservationResponse = createReservation(memberId, bookId);
        assertThat(reservationResponse.code(), is(201));

        assertThat("Body is not null", reservationResponse.body(), notNullValue());

        /*
        reservationId = Integer.parseInt(response.body().string());
        Member memberResponse = getMemberByID(memberId).body();
        assert reservationResponse != null;
        assertThat("Body is not null", reservationResponse, notNullValue());
        assertThat("id should not be null", reservationResponse.getId(), notNullValue());

        */

}
}
