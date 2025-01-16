package api.calls;

import api.mappings.generic.Reservation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.*;
import retrofit2.http.Path;

import java.util.List;

public interface ReservationCalls {
    String RESERVATION = "reservation";
    String RESERVATION_ID = "reservation/{id}";
    String RESERVATION_MEMBER_ID = "reservation/member/{id}";
    String RESERVATION_MEMBER_ID_ACTIVE = "reservation/member/{id}?active=true";
    String RESERVATION_MEMBER_ID_NOT_ACTIVE = "reservation/member/{id}?active=false";
    String RESERVATION_BOOK_ID = "reservation/book/{id}";
    String RESERVATION_BOOK_ID_ACTIVE = "reservation/book/{id}?active=true";
    String RESERVATION_BOOK_ID_NOT_ACTIVE = "reservation/book/{id}?active=false";
    String RESERVATION_MEMBER_BOOK = "reservation/member/{memberId}/book{bookId}";

    @GET(RESERVATION_ID)
    Call<Reservation> getReservationByID(@Path("id") Integer id);

    @PUT(RESERVATION_ID)
    Call<ResponseBody> returnBook(@Path("id") Integer id);

    @POST(RESERVATION_MEMBER_BOOK)
    Call<ResponseBody> createReservation(@Path("memberId") Integer idM, @Path("bookId") Integer idB);

    @GET(RESERVATION)
    Call<List<Reservation>> getAllReservation();

    @GET(RESERVATION_MEMBER_ID_ACTIVE)
    Call<List<Reservation>> getAllReservationByMemberIdActive(@Path("id") Integer id);

    @GET(RESERVATION_MEMBER_ID_NOT_ACTIVE)
    Call<List<Reservation>> getAllReservationByMemberIdNotActive(@Path("id") Integer id);

    @GET(RESERVATION_MEMBER_ID)
    Call<List<Reservation>> getAllReservationByMemberId(@Path("id") Integer id);

    @GET(RESERVATION_BOOK_ID)
    Call<List<Reservation>> getAllReservationByBookId(@Path("id") Integer id);

    @GET(RESERVATION_BOOK_ID_ACTIVE)
    Call<List<Reservation>> getAllReservationByBookIdActive(@Path("id") Integer id);

    @GET(RESERVATION_BOOK_ID_NOT_ACTIVE)
    Call<List<Reservation>> getAllReservationByBookIdNotActive(@Path("id") Integer id);
}
