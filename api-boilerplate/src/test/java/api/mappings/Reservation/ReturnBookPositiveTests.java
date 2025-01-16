package api.mappings.Reservation;

import api.mappings.generic.Book;
import api.mappings.generic.Member;
import api.retrofit.Reservations;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.time.LocalDate;

import static api.retrofit.Books.createBook;
import static api.retrofit.Books.getBookById;
import static api.retrofit.Members.createMember;
import static api.retrofit.Members.deleteMember;
import static api.retrofit.Reservations.createReservation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ReturnBookPositiveTests {


    Integer memberId, bookId1, reservationId;

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

    @SneakyThrows
    @BeforeMethod
    public void setUpReservation(){
        ;
        String responseString= createReservation(memberId, bookId1).body().string();
        reservationId = Integer.parseInt(responseString);
        //createReservation(memberId, bookId2);
    }

    @AfterMethod
    public void cleanUpMember() {
        deleteMember(memberId);
    }

    @Test
    public void returnBook(){
        Response<ResponseBody> returnBook = Reservations.returnBook(reservationId);
        assert (returnBook.code()==204);
        System.out.println(returnBook);
    }
}
