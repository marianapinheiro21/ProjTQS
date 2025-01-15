package api.retrofit;

import api.calls.ReservationCalls;
import api.mappings.generic.Reservation;
import okhttp3.ResponseBody;
import retrofit2.Response;
import java.util.List;

public class Reservations {
    public static final ReservationCalls reservationCalls = new RetrofitBuilder().getRetrofit().create(ReservationCalls.class);

    public static Response<Reservation> getReservationByID(Integer reservationId) {
        try {
            return reservationCalls.getReservationByID(reservationId).execute();
        } catch (final java.lang.Throwable $ex) {
            throw lombok.Lombok.sneakyThrow($ex);
        }
    }

    public static Response<ResponseBody> returnBook(Integer bookId) {
        try {
            return reservationCalls.returnBook(bookId).execute();
        } catch (final java.lang.Throwable $ex) {
            throw lombok.Lombok.sneakyThrow($ex);
        }
    }

    public static Response<ResponseBody> createReservation(Integer memberId, Integer bookId) {
        try {
            return reservationCalls.createReservation(memberId, bookId).execute();
        } catch (final java.lang.Throwable $ex) {
            throw lombok.Lombok.sneakyThrow($ex);
        }
    }

    public static Response<List<Reservation>> getAllReservation() {
        try {
            return reservationCalls.getAllReservation().execute();
        } catch (final java.lang.Throwable $ex) {
            throw lombok.Lombok.sneakyThrow($ex);
        }
    }

    public static Response<Reservation> getAllReservationByMemberId(Integer id) {
        try {
            return reservationCalls.getAllReservationByMemberId(id).execute();
        } catch (final java.lang.Throwable $ex) {
            throw lombok.Lombok.sneakyThrow($ex);
        }
    }

    public static Response<Reservation> getAllReservationByBookId(Integer id) {
        try {
            return reservationCalls.getAllReservationByBookId(id).execute();
        } catch (final java.lang.Throwable $ex) {
            throw lombok.Lombok.sneakyThrow($ex);
        }
    }
}
