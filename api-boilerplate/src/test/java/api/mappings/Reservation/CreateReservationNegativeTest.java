package api.mappings.Reservation;

import api.mappings.generic.Book;
import api.mappings.generic.ErrorResponse;
import api.mappings.generic.Member;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.time.LocalDate;

import static api.retrofit.Books.*;
import static api.retrofit.Members.createMember;
import static api.retrofit.Members.deleteMember;
import static api.retrofit.Reservations.createReservation;
import static api.retrofit.Reservations.returnBook;
import static api.retrofit.generic.Errors.getErrorsResponse;
import static api.validators.ErrorResponseValidator.assertErrorResponse;
import static api.validators.ResponseValidator.assertBadRequest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CreateReservationNegativeTest {

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


    @SneakyThrows
    @Test(description = "Create new reservation with no success")
    public void createReservationNegativeTest1() { //Devolve o erro errado

        Response<ResponseBody> reservationRequest = createReservation(memberId, -1);
        assertBadRequest(reservationRequest);

        ErrorResponse errorResponse = getErrorsResponse(reservationRequest);
        assertErrorResponse(errorResponse, 400, "Bad Request", "Invalid Book ID", "/reservation/member/{memberId}/book-1");

    }

    @SneakyThrows
    @Test(description = "Create new reservation with no success")
    public void createReservationNegativeTest2() { //Devolve o erro errado

        Response<ResponseBody> reservationRequest = createReservation(-1, bookId);
        assertBadRequest(reservationRequest);

        ErrorResponse errorResponse = getErrorsResponse(reservationRequest);
        assertErrorResponse(errorResponse, 400, "Bad Request", "Invalid Member ID", "/reservation/member/-1/book{bookId}");
    }

}
