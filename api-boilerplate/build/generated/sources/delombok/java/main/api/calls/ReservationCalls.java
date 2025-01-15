package api.calls;

import api.mappings.generic.Reservation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;
import retrofit2.http.Path;

import java.util.List;

public interface ReservationCalls {
    String RESERVATION = "reservation";
    String RESERVATION_ID = "reservation/{id}";
    String RESERVATION_MEMBER_ID = "reservation/member/{id}";
    String RESERVATION_BOOK_ID = "reservation/book/{id}";
    String RESERVATION_MEMBER_BOOK = "reservation/member/{memberId}/book{bookId}";

    @GET(RESERVATION_ID)
    Call<Reservation> getReservationByID(@Path("id") Integer id);

    @PUT(RESERVATION_ID)
    Call<ResponseBody> returnBook(@Path("id") Integer id);

    @POST(RESERVATION_MEMBER_BOOK)
    Call<ResponseBody> createReservation(@Path("memberId") Integer idM, @Path("bookId") Integer idB);

    @GET(RESERVATION)
    Call<List<Reservation>> getAllReservation();

    @GET(RESERVATION_MEMBER_ID)
    Call<Reservation> getAllReservationByMemberId(@Path("id") Integer id);

    @GET(RESERVATION_BOOK_ID)
    Call<Reservation> getAllReservationByBookId(@Path("id") Integer id);
}
