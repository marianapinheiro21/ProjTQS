package api.mappings.Book;

import api.mappings.generic.Book;
import api.mappings.generic.ErrorResponse;

import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.testng.annotations.Test;
import retrofit2.Response;

import static api.retrofit.Books.createBook;
import static api.retrofit.generic.Errors.getErrorsResponse;
import static api.validators.ErrorResponseValidator.assertErrorResponse;
import static api.validators.ResponseValidator.assertBadRequest;

public class CreateBookNegativeTests {

    @SneakyThrows
    @Test
    public void createBookNegative1() {
        Book bookRequest = Book.builder()
                //.title("Wicked part 1")
                //.author("Gregory Maguire")
                //.publisher("william morrow company")
                //.editionYear(2000)
                //.edition("Third Edition")
                //.description("Years before Dorothy and her dog crash-land, another little girl makes her presence known in Oz. This girl, Elphaba, is born with emerald-green skin—no easy burden in a land as mean and poor as Oz")
                .isbn("0060987103")
                .build();
        Response<ResponseBody> response = createBook(bookRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request", "Invalid Edition Year", "/book");
    }

    @SneakyThrows
    @Test
    public void createBookNegative2() {
        Book bookRequest = Book.builder()
                //.title("Wicked part 1")
                //.author("Gregory Maguire")
                //.publisher("william morrow company")
                .editionYear(2000)
                //.edition("Third Edition")
                //.description("Years before Dorothy and her dog crash-land, another little girl makes her presence known in Oz. This girl, Elphaba, is born with emerald-green skin—no easy burden in a land as mean and poor as Oz")
                //.isbn("0060987103")
                .build();
        Response<ResponseBody> response = createBook(bookRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request", "Invalid ISBN Number", "/book");
    }

    @SneakyThrows
    @Test
    public void createBookNegative3() {
        Book bookRequest = Book.builder()
                //.title("Wicked part 1")
                //.author("Gregory Maguire")
                //.publisher("william morrow company")
                //.editionYear(2000)
                //.edition("Third Edition")
                //.description("Years before Dorothy and her dog crash-land, another little girl makes her presence known in Oz. This girl, Elphaba, is born with emerald-green skin—no easy burden in a land as mean and poor as Oz")
                //.isbn("0060987103")
                .build();
        Response<ResponseBody> response = createBook(bookRequest);
        assertBadRequest(response);

        ErrorResponse errorResponse = getErrorsResponse(response);
        assertErrorResponse(errorResponse, 400, "Bad Request", "Invalid Edition Year", "/book");
    }
}
