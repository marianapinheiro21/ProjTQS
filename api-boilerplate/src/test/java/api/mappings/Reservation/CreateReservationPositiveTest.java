package api.mappings.Reservation;

import api.mappings.generic.Member;
import api.mappings.generic.Book;
import api.mappings.generic.Reservation;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDate;

import static api.retrofit.Books.*;
import static api.retrofit.Members.*;
import static api.retrofit.Reservations.*;
import static jdk.dynalink.linker.support.Guards.isNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CreateReservationPositiveTest {
    Integer memberId, bookId, reservationId;

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
        memberId = Integer.parseInt(response.body().string());
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
        bookId = Integer.parseInt(response.body().string());
        System.out.println(getBookById(bookId));
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



    @SneakyThrows
    @Test(description = "Create new reservation with success")
    public void createReservationSuccess(){ //works, not properly tested yet

        Response<ResponseBody> reservationRequest = createReservation(memberId, bookId);
        assertThat(reservationRequest.code(), is(201));

        assertThat("Body is not null", reservationRequest.body(), notNullValue());
        reservationId = Integer.parseInt(reservationRequest.body().string());

        Reservation reservationResponse = getReservationByID(reservationId).body();
        assertThat("id should not be null", reservationResponse.getId(), notNullValue());
        assertThat("Member's Id is not the expected", reservationResponse.getMemberId(), is(memberId));
        assertThat("Book's Id is not the expected", reservationResponse.getBookId(), is(bookId));

    }


}
