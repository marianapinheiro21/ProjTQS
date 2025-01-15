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

import static api.retrofit.Books.createBook;
import static api.retrofit.Books.deleteBook;
import static api.retrofit.Members.*;
import static api.retrofit.Reservations.createReservation;
import static api.retrofit.Reservations.getReservationByID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CreateReservationPositiveTest {
    Integer memberId, bookId;

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
                .title("Wicked")
                .author("Gregory Maguire")
                .publisher("william morrow company")
                .editionYear(2000)
                .edition("Third Edition")
                .description("Years before Dorothy and her dog crash-land, another little girl makes her presence known in Oz. This girl, Elphaba, is born with emerald-green skin—no easy burden in a land as mean and poor as Oz, where superstition and magic are not strong enough to explain or overcome the natural disasters of flood and famine. Still, Elphaba is smart, and by the time she enters Shiz University, she becomes a member of a charmed circle of Oz’s most promising young citizens. \n  But Elphaba’s Oz is no utopia. The Wizard’s secret police are everywhere. Animals—those creatures with voices, souls, and minds—are threatened with exile. Young Elphaba, green and wild and misunderstood, is determined to protect the Animals—even if it means combating the mysterious Wizard, even if it means risking her single chance at romance. Ever wiser in guilt and sorrow, she can find herself grateful when the world declares her a witch. And she can even make herself glad for that young girl from Kansas.")
                .isbn("0060987103")
                .build();
        Response<ResponseBody> response = createBook(bookRequest);
        assertThat(response.code(), is(201));

        assertThat("Body is not null", response.body(), notNullValue());
        bookId = Integer.parseInt(response.body().string());
    }



    @AfterMethod
    public void cleanUpMember() {
        deleteMember(memberId);
    }

    @AfterMethod
    public void cleanUpBook() {
        deleteBook(bookId);
    }

    @SneakyThrows
    @Test(description = "Create new reservation with success")
    public void createReservationSuccess(){ //works, not properly tested yet
        Integer memberId = 2;
        Integer bookId = 1;

        Response<ResponseBody> reservationResponse = createReservation(memberId, bookId);
        assertThat(reservationResponse.code(), is(201));

        assertThat("Body is not null", reservationResponse.body(), notNullValue());
        Integer reservationId = Integer.parseInt(reservationResponse.body().string());

        assertThat("Body is not null", reservationResponse, notNullValue());
        Reservation newReservation = getReservationByID(reservationId).body();
        //assertThat("id should not be null", reservationResponse.body()., notNullValue());

}
}
