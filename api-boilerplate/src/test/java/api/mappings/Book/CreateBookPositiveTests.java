package api.mappings.Book;

import api.mappings.generic.Book;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import static api.retrofit.Books.*;
import static api.validators.ResponseValidator.assertBook;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CreateBookPositiveTests {

    Integer bookId;
    @AfterMethod
    public void cleanUpBook() {
        deleteBook(bookId);
    }

    @SneakyThrows
    @Test
    public void createBookPositive1() {
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
        String responseString= response.body().string();
        bookId = Integer.parseInt(responseString);
        Book bookResponse = getBookById(bookId).body();

        assertBook(response, bookRequest, bookResponse);
    }


    @SneakyThrows
    @Test
    public void createBookPositive2() {
        Book bookRequest = Book.builder()
                //.title("Wicked part 1")
                //.author("Gregory Maguire")
                //.publisher("william morrow company")
                .editionYear(2000)
                //.edition("Third Edition")
                //.description("Years before Dorothy and her dog crash-land, another little girl makes her presence known in Oz. This girl, Elphaba, is born with emerald-green skin—no easy burden in a land as mean and poor as Oz")
                .isbn("0060987103")
                .build();
        Response<ResponseBody> response = createBook(bookRequest);
        assertThat(response.code(), is(201));
        String responseString= response.body().string();
        bookId = Integer.parseInt(responseString);
        Book bookResponse = getBookById(bookId).body();

        assertBook(response, bookRequest, bookResponse);
    }

}
