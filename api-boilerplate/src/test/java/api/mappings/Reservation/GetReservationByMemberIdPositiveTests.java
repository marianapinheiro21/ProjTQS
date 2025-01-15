package api.mappings.Reservation;

import api.mappings.generic.Book;
import api.mappings.generic.Member;
import api.mappings.generic.Reservation;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.time.LocalDate;
import java.util.List;

import static api.retrofit.Books.*;
import static api.retrofit.Members.createMember;
import static api.retrofit.Members.deleteMember;
import static api.retrofit.Reservations.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetReservationByMemberIdPositiveTests {

    Integer memberId, bookId1, bookId2;

    @SneakyThrows
    @BeforeMethod
    public void setUpMember(){
        Member memberRequest = Member.builder()
                .firstName("Joaquim")
                .lastName("Daniel")
                .address("Rua das Flores")
                .postalCode("3898-098")
                .city("Amarante")
                .country("Portugal")
                .phoneNumber(963852741)
                .nif(123456789)
                .email("john@doe.com")
                .birthDate(LocalDate.parse("1999-09-09"))
                .registrationDate(LocalDate.now())
                .build();
        Response<ResponseBody> response = createMember(memberRequest);
        memberId = Integer.parseInt(response.body().string());
    }

    @SneakyThrows
    @BeforeMethod
    public void setUpBook1(){
        Book bookRequest = Book.builder()
                .title("Wicked part 1")
                .author("Gregory Maguire")
                .publisher("william morrow company")
                .editionYear(2000)
                .edition("Third Edition")
                .description("Years before Dorothy and her dog crash-land, another little girl makes her presence known in Oz. This girl, Elphaba, is born with emerald-green skin—no easy burden in a land as mean and poor as Oz")
                .isbn("0060987103")
                .build();
        Response<ResponseBody> response = createBook(bookRequest);
        assertThat(response.code(), is(201));
        String responseString= /*Integer.parseInt(*/response.body().string();
        bookId1 = Integer.parseInt(responseString);
        System.out.println(getBookById(bookId1));
    }

    /*@SneakyThrows
    @BeforeMethod
    public void setUpBook2(){
        Book bookRequest = Book.builder()
                .title("Wicked part 2")
                .author("Gregory Maguire")
                .publisher("william morrow company")
                .editionYear(2000)
                .edition("Third Edition")
                .description("Years before Dorothy and her dog crash-land, another little girl makes her presence known in Oz. This girl, Elphaba, is born with emerald-green skin—no easy burden in a land as mean and poor as Oz")
                .isbn("0060975103")
                .build();
        Response<ResponseBody> response = createBook(bookRequest);
        assert response.body() != null;
        assertThat(response.code(), is(201));
        String responseString= /*Integer.parseInt(response.body().string();
        bookId2 = Integer.parseInt(responseString);
        System.out.println(getBookById(bookId2));
    }*/

    @SneakyThrows
    @BeforeMethod
    public void setUpReservation(){
        createReservation(memberId, bookId1);
        //createReservation(memberId, bookId2);
    }

    @AfterMethod
    public void cleanUpMember() {
        deleteMember(memberId);
    }

    @AfterMethod
    public void cleanUpBook() {
        deleteBook(bookId1);
        //deleteBook(bookId2);
    }

    @Test
    public void getAllReservationsByMemberId() {
        Response<List<Reservation>> reservationResponse =getAllReservationByMemberId(memberId);

        assertThat("Body is not null", reservationResponse.body(), notNullValue());
        assertThat("Reservation list should not be empty", reservationResponse.body(), not(empty()));

        for (Reservation reservation : reservationResponse.body()) {
            assertThat("Reservation ID should not be null", reservation.getId(), notNullValue());
            assertThat("Member ID should match", reservation.getMemberId(), is(memberId));
            assertThat("Book ID should not be null", reservation.getBookId(), notNullValue());
            assertThat("Reservation date should not be null", reservation.getReservationDate(), notNullValue());
            assertThat("Return date should be set correctly", reservation.getReturnDate(), anyOf(is(notNullValue()), is(nullValue())));
        }
    }

    @Test
    public void getAllActiveReservationsByMemberId() {
        Response<List<Reservation>> reservationResponse =getAllReservationByMemberIdActive(memberId);

        assertThat("Body is not null", reservationResponse.body(), notNullValue());
        System.out.println("########################################################");
        System.out.println(reservationResponse.body());

        assertThat("Reservation list should not be empty", reservationResponse.body(), not(empty()));
        for (Reservation reservation : reservationResponse.body()) {
            assertThat("Reservation ID should not be null", reservation.getId(), notNullValue());
            assertThat("Member ID should match", reservation.getMemberId(), is(memberId));
            assertThat("Book ID should not be null", reservation.getBookId(), notNullValue());
            assertThat("Reservation date should not be null", reservation.getReservationDate(), notNullValue());
            assertThat("Return date should be set correctly", reservation.getReturnDate(), anyOf(is(notNullValue()), is(nullValue())));
        }

    }

    @Test
    public void getAllNonActiveReservationsByMemberId() {
        Response<List<Reservation>> reservationResponse =getAllReservationByMemberIdNotActive(memberId);

        assertThat("Body is not null", reservationResponse.body(), notNullValue());
        System.out.println("########################################################");
        System.out.println(reservationResponse.body());

        assertThat("Reservation list should be empty", reservationResponse.body(), empty());


    }

}
