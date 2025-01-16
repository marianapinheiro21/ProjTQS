package api.mappings.Book;

import api.mappings.generic.Book;
import api.mappings.generic.Member;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import static api.retrofit.Books.*;
import static api.retrofit.Members.deleteMember;
import static api.retrofit.Members.getMemberByID;
import static api.validators.ResponseValidator.assertNoContent;
import static api.validators.ResponseValidator.assertNotFound;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DeleteBookPositiveTest {
    Book bookRequest;
    Integer bookId1;

    @SneakyThrows
    @BeforeMethod
    public void setUpBook1(){
        bookRequest = Book.builder()
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
    @Test(description = "Delete Book with Success")
    public void deleteBookSuccess() {
        Response<ResponseBody> response = deleteBook(this.bookId1);
        assertNoContent(response);
        assert (response.code() == 204);
        Response<Member> responseGet = getMemberByID(bookId1);
        assertNotFound(responseGet);
    }

    /*@Test(description = "Delete Book with Success")
    public void deleteBookSuccess2() {
        Response<ResponseBody> response = deleteBook(this.bookId1, true);
        assertNoContent(response);
        assert (response.code() == 204);
        Response<Member> responseGet = getMemberByID(bookId1);
        assertNotFound(responseGet);
    }

    @Test(description = "Delete Book with Success")
    public void deleteBookSuccess3() {
        Response<ResponseBody> response = deleteBook(this.bookId1, false);
        assertNoContent(response);
        assert (response.code() == 204);
        Response<Member> responseGet = getMemberByID(bookId1);
        assertNotFound(responseGet);
    } */

}
