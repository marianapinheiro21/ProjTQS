package api.retrofit;

import api.calls.ReservationCalls;
import api.mappings.generic.Reservation;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import retrofit2.Response;

import java.util.List;


public class Reservations {
    public static  final ReservationCalls reservationCalls = new RetrofitBuilder().getRetrofit().create(ReservationCalls.class);

    @SneakyThrows
    public static Response<Reservation> getReservationByID(Integer reservationId) {
        return reservationCalls.getReservationByID(reservationId).execute();
    }

    @SneakyThrows
    public static Response<ResponseBody> returnBook(Integer bookId) {
        return reservationCalls.returnBook(bookId).execute();
    }

    @SneakyThrows
    public static Response<ResponseBody> createReservation(Integer memberId, Integer bookId) {
        return reservationCalls.createReservation(memberId, bookId).execute();
    }

    @SneakyThrows
    public static Response<List<Reservation>> getAllReservation(){
        return reservationCalls.getAllReservation().execute();
    }

    @SneakyThrows
    public static Response<Reservation> getAllReservationByMemberId(Integer id){
        return reservationCalls.getAllReservationByMemberId(id).execute();
    }

    @SneakyThrows
    public static Response<Reservation> getAllReservationByBookId(Integer id){
        return reservationCalls.getAllReservationByBookId(id).execute();
    }

}
