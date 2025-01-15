package api.mappings.Reservation;

import api.mappings.generic.Book;
import api.mappings.generic.Member;
import api.mappings.generic.Reservation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDate;

import static api.retrofit.Books.*;
import static api.retrofit.Members.createMember;
import static api.retrofit.Members.deleteMember;
import static api.retrofit.Reservations.*;
import static api.validators.ResponseValidator.assertOk;
import static java.lang.Integer.parseInt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class GetReservationPositiveTest {

    Integer memberId, bookId, reservationId;
    Reservation reservationRequest;
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
        assertThat(response.code(), is(201));

        assertThat("Body is not null", response.body(), notNullValue());
        memberId = parseInt(response.body().string());
    }

    @SneakyThrows
    @BeforeMethod
    public void setUpBook(){
        Book bookRequest = Book.builder()
                .title("Wicked part 2")
                .author("Gregory Maguire")
                .publisher("william morrow company")
                .editionYear(2000)
                .edition("Third Edition")
                .description("Years before Dorothy and her dog crash-land, another little girl makes her presence known in Oz. This girl, Elphaba, is born with emerald-green skin—no easy burden in a land as mean and poor as Oz")
                .isbn("0060987103")
                .build();
        Response<ResponseBody> response = createBook(bookRequest);
        assertThat(response.code(), is(201));

        assertThat("Body is not null", response.body(), notNullValue());
        bookId = parseInt(response.body().string());
        System.out.println(getBookById(bookId));
    }

    @SneakyThrows
    @BeforeMethod
    public void setUpReservation(){
        Response<ResponseBody> reservationRequest =  createReservation(memberId, bookId);
        assert reservationRequest.body() != null;

        reservationId = Integer.parseInt(reservationRequest.body().string());
    }


    @AfterMethod
    public void cleanUpMember() {
        deleteMember(memberId);
    }

    @AfterMethod
    public void cleanUpBook() {
        deleteBook(bookId);
    }

    @AfterMethod
    public void cleanUpReservation() {
        returnBook(reservationId); //Não dando para apagar a reserva feita em testes, o mínimo que posso fazer é que ela fique completa
    }

    @Test(description ="Get reservation by ID")
    public void getReservationById1( ) {

        Response<Reservation> response = getReservationByID(reservationId);
        assertOk(response);
        Reservation reservationResponse = response.body();
        System.out.println(reservationResponse);

        Reservation reservationRequest2= (getReservationByID(reservationId).body());
        System.out.println(reservationRequest2);


        assertThat("Body is not null", response.body(), notNullValue());
        assertThat("Reservation's ID is not the expected", reservationResponse.getId(), is(reservationRequest2.getId()));
        assertThat("Reservation's Member ID is not the expected", reservationResponse.getMemberId(), is(reservationRequest2.getMemberId()));
        assertThat("Reservation's Book ID is not the expected", reservationResponse.getBookId(), is(reservationRequest2.getBookId()));
        assertThat("Reservation's Date is not the expected", reservationResponse.getReservationDate(), is(reservationRequest2.getReservationDate()));
        assertThat("Reservation's Return Date is not the expected", reservationResponse.getReturnDate(), is(reservationRequest2.getReturnDate()));

    }

    @Test(description ="Get reservation by ID")
    public void getReservationById2( ) {

        Response<Reservation> response = getReservationByID(36);
        assertOk(response);
        Reservation reservationResponse = response.body();

        assertThat("Body is not null", response.body(), notNullValue());
        assertThat("Reservation's ID is not the expected", reservationResponse.getId(), is(36));
        assertThat("Reservation's Member ID is not the expected", reservationResponse.getMemberId(), is(2));
        assertThat("Reservation's Book ID is not the expected", reservationResponse.getBookId(), is(2));
        //assertThat("Reservation's Date is not the expected", reservationResponse.getReservationDate(), is(<2025-01-15T14:37:35.005009>));
        //assertThat("Reservation's Return Date is not the expected", reservationResponse.getReturnDate(), is(null));

    }
}
