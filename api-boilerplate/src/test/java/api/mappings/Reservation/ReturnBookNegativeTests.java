package api.mappings.Reservation;

import api.mappings.generic.Book;
import api.mappings.generic.ErrorResponse;
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

public class ReturnBookNegativeTests {

    @Test
    public void returnBookNegative1(){
        Response<ResponseBody> returnBook = Reservations.returnBook(-1);
        assert (returnBook.code()==400);
        System.out.println(returnBook);
    }

    @Test
    public void returnBookNegative2(){
        Response<ResponseBody> returnBook = Reservations.returnBook(88888);
        assert (returnBook.code()==404);
        System.out.println(returnBook);
    }
}
