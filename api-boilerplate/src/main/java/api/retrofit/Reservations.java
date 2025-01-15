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
    public static Response<List<Reservation>> getAllReservationByMemberId(Integer id){
        return reservationCalls.getAllReservationByMemberId(id).execute();
    }

    @SneakyThrows
    public static Response<List<Reservation>> getAllReservationByMemberIdActive(Integer id){
        return reservationCalls.getAllReservationByMemberIdActive(id).execute();
    }

    @SneakyThrows
    public static Response<List<Reservation>> getAllReservationByMemberIdNotActive(Integer id){
        return reservationCalls.getAllReservationByMemberIdNotActive(id).execute();
    }

    @SneakyThrows
    public static Response<List<Reservation>> getAllReservationByBookId(Integer id){
        return reservationCalls.getAllReservationByBookId(id).execute();
    }

    @SneakyThrows
    public static Response<List<Reservation>> getAllReservationByBookIdActive(Integer id){
        return reservationCalls.getAllReservationByBookIdActive(id).execute();
    }

    @SneakyThrows
    public static Response<List<Reservation>> getAllReservationByBookIdNotActive(Integer id){
        return reservationCalls.getAllReservationByBookIdNotActive(id).execute();
    }
}
